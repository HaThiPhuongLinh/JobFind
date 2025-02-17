package com.jobfind.dto.response;

import com.jobfind.dto.dto.ApplicationStatusDTO;
import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.dto.dto.JobDTO;
import com.jobfind.dto.dto.JobSeekerProfileDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ApplicationStatusResponse {
    private CompanyDTO company;
    private JobDTO job;
    private JobSeekerProfileDTO jobSeekerProfile;
    private List<ApplicationStatusDTO> statusDTOList;
}
