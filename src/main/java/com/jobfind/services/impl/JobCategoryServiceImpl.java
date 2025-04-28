package com.jobfind.services.impl;

import com.jobfind.dto.response.JobCategoryResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.JobCategory;
import com.jobfind.repositories.JobCategoryRepository;
import com.jobfind.services.IJobCatrgoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobCategoryServiceImpl implements IJobCatrgoryService {
    private final JobCategoryRepository jobCategoryRepository;

    @Override
    public List<JobCategoryResponse> getAllJobCategories() {
        List<JobCategory> jobCategories = jobCategoryRepository.findAll();
        return jobCategories.stream()
                .map(jobCategory -> JobCategoryResponse.builder()
                        .jobCategoryId(jobCategory.getJobCategoryId())
                        .name(jobCategory.getName())
                        .build())
                .toList();
    }

    @Override
    public void addJobCategory(String jobCategoryName) {
        if(jobCategoryRepository.existsByName(jobCategoryName)){
            throw new BadRequestException("Job Category already exists");
        }

        JobCategory jobCategory = JobCategory.builder()
                .name(jobCategoryName)
                .build();
        jobCategoryRepository.save(jobCategory);
    }

    @Override
    public void deleteJobCategory(Integer jobCategoryId) {
        jobCategoryRepository.deleteById(jobCategoryId);
    }

    @Override
    public void updateJobCategory(Integer jobCategoryId, String jobCategoryName) {
        JobCategory jobCategory = jobCategoryRepository.findById(jobCategoryId).orElseThrow(
                () -> new BadRequestException("Job Category not found"));
        jobCategory.setName(jobCategoryName);
        jobCategoryRepository.save(jobCategory);
    }
}
