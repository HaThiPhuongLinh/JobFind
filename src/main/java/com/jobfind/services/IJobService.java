package com.jobfind.services;

import com.jobfind.dto.dto.JobDTO;
import com.jobfind.dto.request.CreateJobRequest;
import org.springframework.validation.BindingResult;

public interface IJobService {
    void createJob(CreateJobRequest request, BindingResult bindingResult);
    JobDTO getJobByID(Integer jobId);
}