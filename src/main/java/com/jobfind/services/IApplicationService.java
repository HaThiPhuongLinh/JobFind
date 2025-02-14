package com.jobfind.services;

import com.jobfind.dto.request.ApplicationRequest;
import com.jobfind.models.ApplicationStatusHistory;

import java.util.List;

public interface IApplicationService {
    void applyForJob(ApplicationRequest request);
    List<ApplicationStatusHistory> getApplicationStatusHistory(Integer applicationId);
}
