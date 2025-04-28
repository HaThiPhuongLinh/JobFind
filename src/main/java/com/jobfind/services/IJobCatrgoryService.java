package com.jobfind.services;

import com.jobfind.dto.response.JobCategoryResponse;
import com.jobfind.models.JobCategory;

import java.util.List;

public interface IJobCatrgoryService {
    List<JobCategoryResponse> getAllJobCategories();
    void addJobCategory(String jobCategoryName);
    void deleteJobCategory(Integer jobCategoryId);
    void updateJobCategory(Integer jobCategoryId, String jobCategoryName);
}
