package com.jobfind.dto.response;

import com.jobfind.models.enums.JobType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedJobResponse {
    private Integer jobId;
    private Integer companyId;
    private String jobName;
    private String companyName;
    private double salaryMin;
    private double salaryMax;
    private JobType jobType;
    private String location;
    private LocalDateTime postedAt;
    private LocalDate savedAt;
}
