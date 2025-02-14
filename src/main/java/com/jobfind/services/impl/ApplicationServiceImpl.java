package com.jobfind.services.impl;

import com.jobfind.dto.request.ApplicationRequest;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Application;
import com.jobfind.models.ApplicationStatusHistory;
import com.jobfind.models.Job;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.enums.ApplicationStatus;
import com.jobfind.repositories.ApplicationRepository;
import com.jobfind.repositories.ApplicationStatusHistoryRepository;
import com.jobfind.repositories.JobRepository;
import com.jobfind.repositories.JobSeekerProfileRepository;
import com.jobfind.services.IApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements IApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final ApplicationStatusHistoryRepository historyRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    public void applyForJob(ApplicationRequest request) {
        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new BadRequestException("Job not found"));

        JobSeekerProfile jobSeeker = jobSeekerProfileRepository.findById(request.getJobSeekerProfileId())
                .orElseThrow(() -> new BadRequestException("Job seeker profile not found"));

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
    public List<ApplicationStatusHistory> getApplicationStatusHistory(Integer applicationId) {
        return historyRepository.findByApplicationApplicationId(applicationId);
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
