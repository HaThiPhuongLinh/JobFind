package com.jobfind.services.impl;

import com.jobfind.dto.dto.ApplicationStatusDTO;
import com.jobfind.dto.request.ApplicationRequest;
import com.jobfind.dto.response.ApplicationStatusResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Application;
import com.jobfind.models.ApplicationStatusHistory;
import com.jobfind.models.Job;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.enums.ApplicationStatus;
import com.jobfind.converters.JobConverter;
import com.jobfind.converters.JobSeekerProfileConverter;
import com.jobfind.repositories.ApplicationRepository;
import com.jobfind.repositories.ApplicationStatusHistoryRepository;
import com.jobfind.repositories.JobRepository;
import com.jobfind.repositories.JobSeekerProfileRepository;
import com.jobfind.services.IApplicationService;
import lombok.RequiredArgsConstructor;
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
    private final JobSeekerProfileConverter jobSeekerProfileConverter;
    private final JobConverter jobConverter;

    public void applyForJob(ApplicationRequest request) {
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new BadRequestException("Job not found"));

        JobSeekerProfile jobSeeker = jobSeekerProfileRepository.findById(request.getJobSeekerProfileId())
                .orElseThrow(() -> new BadRequestException("Job seeker profile not found"));

        applicationRepository.findByJobJobIdAndJobSeekerProfileProfileId(request.getJobId(), request.getJobSeekerProfileId())
                .orElseThrow(() ->  new BadRequestException("Application already exist"));

        Application application = Application.builder()
                .job(job)
                .jobSeekerProfile(jobSeeker)
                .appliedAt(LocalDateTime.now())
                .applicationStatus(ApplicationStatus.PENDING)
                .build();
        applicationRepository.save(application);
        saveApplicationStatusHistory(application, ApplicationStatus.PENDING);
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

    private void saveApplicationStatusHistory(Application application, ApplicationStatus status) {
        ApplicationStatusHistory history = ApplicationStatusHistory.builder()
                .application(application)
                .applicationStatus(status)
                .time(LocalDateTime.now())
                .build();
        historyRepository.save(history);
    }
}
