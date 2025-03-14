package com.jobfind.dto.response;

import com.jobfind.dto.dto.ApplicationStatusDTO;
import com.jobfind.dto.dto.JobDTO;
import com.jobfind.dto.dto.JobSeekerProfileDTO;
import com.jobfind.dto.dto.ResumeDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusResponse {
    private JobDTO job;
    private JobSeekerProfileDTO jobSeekerProfile;
    private ResumeDTO resumeApplied;
    private List<ApplicationStatusDTO> statusDTOList;
}
