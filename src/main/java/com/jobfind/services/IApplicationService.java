package com.jobfind.services;

import com.jobfind.dto.request.ApplicationRequest;
import com.jobfind.dto.response.ApplicationStatusResponse;

public interface IApplicationService {
    void applyForJob(ApplicationRequest request);
    ApplicationStatusResponse getApplicationStatusHistory(Integer applicationId);
}
