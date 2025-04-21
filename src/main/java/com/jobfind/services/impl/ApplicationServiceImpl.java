package com.jobfind.services.impl;

import com.jobfind.constants.JobFindConstant;
import com.jobfind.converters.ResumeConverter;
import com.jobfind.dto.dto.ApplicationStatusDTO;
import com.jobfind.dto.dto.NotificationDTO;
import com.jobfind.dto.request.ApplicationRequest;
import com.jobfind.dto.request.CreateNotiRequest;
import com.jobfind.dto.response.ApplicationOfJobResponse;
import com.jobfind.dto.response.ApplicationStatusResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.*;
import com.jobfind.models.enums.ApplicationStatus;
import com.jobfind.converters.JobConverter;
import com.jobfind.converters.JobSeekerProfileConverter;
import com.jobfind.repositories.*;
import com.jobfind.services.IApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements IApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final ApplicationStatusHistoryRepository historyRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final ResumeRepository resumeRepository;
    private final NotificationServiceImplService notificationServiceImpl;
    private final JobSeekerProfileConverter jobSeekerProfileConverter;
    private final ResumeConverter resumeConverter;
    private final JobConverter jobConverter;
    private final SimpMessagingTemplate messagingTemplate;

    public void applyForJob(ApplicationRequest request) {
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new BadRequestException("Job not found"));

        if(!job.getIsActive() || job.getIsDeleted() || !job.getIsApproved()){
            throw new BadRequestException("Job is not available for application");
        }

        JobSeekerProfile jobSeeker = jobSeekerProfileRepository.findById(request.getJobSeekerProfileId())
                .orElseThrow(() -> new BadRequestException("Job seeker profile not found"));

        Resume resume = resumeRepository.findById(request.getResumeId())
                .orElseThrow(() -> new BadRequestException("Resume not found"));

        List<Application> existingApplications = applicationRepository.findByJobJobIdAndJobSeekerProfileProfileId(
                request.getJobId(), request.getJobSeekerProfileId()
        );

        if (!existingApplications.isEmpty()) {
            throw new BadRequestException("Application already exists");
        }

        Application application = Application.builder()
                .job(job)
                .jobSeekerProfile(jobSeeker)
                .resume(resume)
                .appliedAt(LocalDateTime.now())
                .applicationStatus(ApplicationStatus.PENDING)
                .build();
        applicationRepository.save(application);
        saveApplicationStatusHistory(application, ApplicationStatus.PENDING);
    }

    @Override
    public List<ApplicationOfJobResponse> getApplicationOfJob(Integer jobId) {
        List<Application> applications = applicationRepository.findByJobJobId(jobId);

        if(applications.isEmpty()){
            throw new BadRequestException("No applications found for the given job ID");
        }

        return applications.stream()
                .map(application -> ApplicationOfJobResponse.builder()
                        .applicationId(application.getApplicationId())
                        .JobSeekerProfileDTO(jobSeekerProfileConverter.convertToJobSeekerProfileDTO(application.getJobSeekerProfile()))
                        .appliedAt(application.getAppliedAt())
                        .status(application.getApplicationStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationStatusResponse getApplicationStatusHistory(Integer applicationId) {
        List<ApplicationStatusHistory> list = historyRepository.findByApplicationApplicationId(applicationId);

        if(list.isEmpty()){
            throw new BadRequestException("No history found for the given application ID");
        }

        return ApplicationStatusResponse.builder()
                .job(jobConverter.convertToJobDTO(list.get(0).getApplication().getJob()))
                .jobSeekerProfile(jobSeekerProfileConverter.convertToJobSeekerProfileDTO(list.get(0).getApplication().getJobSeekerProfile()))
                .resumeApplied(resumeConverter.convertToResumeDTO(list.get(0).getApplication().getResume()))
                .statusDTOList(
                        list.stream().map(
                                history -> ApplicationStatusDTO.builder()
                                        .status(history.getApplicationStatus())
                                        .time(history.getTime())
                                        .build())
                                .collect(Collectors.toList())
                        )
                .build();
    }

    @Override
    public void updateApplicationStatus(Integer applicationId, String status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BadRequestException("Application not found"));

        ApplicationStatus newStatus = ApplicationStatus.valueOf(status);
        application.setApplicationStatus(newStatus);
        applicationRepository.save(application);

        saveApplicationStatusHistory(application, newStatus);

        CreateNotiRequest notificationRequest = CreateNotiRequest.builder()
                .applicationId(application.getApplicationId())
                .userId(application.getJobSeekerProfile().getUser().getUserId())
                .content("Your application status has been updated to: " + newStatus)
                .build();

        NotificationDTO notification = notificationServiceImpl.createNoti(notificationRequest);
        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_NOTIFICATION  + application.getJobSeekerProfile().getUser().getUserId(), notification);
    }

    private void saveApplicationStatusHistory(Application application, ApplicationStatus status) {
        ApplicationStatusHistory history = ApplicationStatusHistory.builder()
                .application(application)
                .applicationStatus(status)
                .time(LocalDateTime.now())
                .build();
        historyRepository.save(history);
    }
}
