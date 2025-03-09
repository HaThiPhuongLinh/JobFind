package com.jobfind.services.impl;

import com.jobfind.dto.response.SavedJobResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Job;
import com.jobfind.models.SavedJob;
import com.jobfind.models.User;
import com.jobfind.repositories.JobRepository;
import com.jobfind.repositories.SavedJobRepository;
import com.jobfind.repositories.UserRepository;
import com.jobfind.services.ISavedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedJobServiceImpl implements ISavedJobService {
    private final SavedJobRepository savedJobRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public void saveJob(Integer jobId, Integer userId) {
        if (savedJobRepository.findByJobJobIdAndUserUserId(jobId, userId).isPresent()) {
            throw new BadRequestException("Job already saved");
        }

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new BadRequestException("Job not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        SavedJob savedJob = SavedJob.builder()
                .job(job)
                .user(user)
                .savedAt(LocalDate.now())
                .build();

        savedJobRepository.save(savedJob);
    }

    @Override
    public void unsaveJob(Integer jobId, Integer userId) {
        SavedJob savedJob = savedJobRepository.findByJobJobIdAndUserUserId(jobId, userId)
                .orElseThrow(() -> new BadRequestException("Saved job not found"));

        savedJobRepository.delete(savedJob);
    }

    @Override
    public List<SavedJobResponse> getListSavedJobs(Integer userId) {
        List<SavedJob> list = savedJobRepository.findByUserUserId(userId);

        return list.stream()
                .map(savedJob -> SavedJobResponse.builder()
                        .jobId(savedJob.getJob().getJobId())
                        .companyId(savedJob.getJob().getCompany().getCompanyId())
                        .jobName(savedJob.getJob().getTitle())
                        .companyName(savedJob.getJob().getCompany().getCompanyName())
                        .salaryMin(savedJob.getJob().getSalaryMin())
                        .salaryMax(savedJob.getJob().getSalaryMax())
                        .jobType(savedJob.getJob().getJobType())
                        .location(savedJob.getJob().getLocation())
                        .postedAt(savedJob.getJob().getPostedAt())
                        .savedAt(savedJob.getSavedAt())
                        .build())
                .toList();
    }
}
