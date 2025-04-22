package com.jobfind.services;

import com.jobfind.dto.request.ApplicationRequest;
import com.jobfind.dto.response.ApplicationOfJobResponse;
import com.jobfind.dto.response.ApplicationStatusResponse;

import java.util.List;

public interface IApplicationService {
    void applyForJob(ApplicationRequest request);
    List<ApplicationOfJobResponse> getApplicationOfJob(Integer jobId);
    List<ApplicationStatusResponse> getApplicationOfJobByJobSeeker(Integer jobSeekerId);
    ApplicationStatusResponse getApplicationStatusHistory(Integer applicationId);
    void updateApplicationStatus(Integer applicationId, String status);
}
