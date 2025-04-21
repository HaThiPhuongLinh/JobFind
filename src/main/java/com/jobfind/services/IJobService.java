package com.jobfind.services;

import com.jobfind.dto.dto.JobDTO;
import com.jobfind.dto.request.CreateJobRequest;
import com.jobfind.dto.request.UpdateJobRequest;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IJobService {
    void createJob(CreateJobRequest request, BindingResult bindingResult);
    JobDTO getJobByID(Integer jobId);
    List<JobDTO> searchJobs(String keyword, List<String> locations, List<Integer> jobCategoryIds);
    List<JobDTO> getJobsByCompanyId(Integer companyId, Integer id);
    List<JobDTO> getJobsByCategory(Integer categoryId);
    void updateJob(UpdateJobRequest request, BindingResult bindingResult);
    void deleteJob(Integer jobId);
    void approveJob(Integer jobId);
}