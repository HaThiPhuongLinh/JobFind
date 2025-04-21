package com.jobfind.dto.response;

import com.jobfind.models.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationOfJobResponse {
    private Integer applicationId;
    private com.jobfind.dto.dto.JobSeekerProfileDTO JobSeekerProfileDTO;
    private LocalDateTime appliedAt;
    private ApplicationStatus status;
}
