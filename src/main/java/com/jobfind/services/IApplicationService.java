package com.jobfind.services;

import com.jobfind.dto.dto.ChartDataDTO;
import com.jobfind.dto.dto.CompanyJobStatsDTO;
import com.jobfind.dto.dto.RecentApplicationDTO;
import com.jobfind.dto.dto.RegionChartDataDTO;
import com.jobfind.dto.request.ApplicationRequest;
import com.jobfind.dto.response.ApplicationOfJobResponse;
import com.jobfind.dto.response.ApplicationStatusResponse;
import com.jobfind.models.Application;

import java.util.List;

public interface IApplicationService {
    List<ApplicationStatusResponse> getAllApplications();
    void applyForJob(ApplicationRequest request);
    List<ApplicationOfJobResponse> getApplicationOfJob(Integer jobId);
    List<ApplicationStatusResponse> getApplicationOfJobByJobSeeker(Integer jobSeekerId);
    ApplicationStatusResponse getApplicationStatusHistory(Integer applicationId);
    void updateApplicationStatus(Integer applicationId, String status);
    List<RecentApplicationDTO> getRecentApplications();
    ChartDataDTO getApplicationTrends(String type, Integer month);
    RegionChartDataDTO getActiveRegions(String type, Integer month);
    CompanyJobStatsDTO getCompanyJobStats(String type, Integer month);
}
