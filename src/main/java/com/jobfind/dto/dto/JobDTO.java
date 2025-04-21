package com.jobfind.dto.dto;

import com.jobfind.models.JobCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@Data
public class JobDTO {
    private Integer jobId;
    private CompanyDTO company;
    private String title;
    private String description;
    private String requirements;
    private String benefits;
    private Double salaryMin;
    private Double salaryMax;
    private String jobType;
    private String location;
    private LocalDateTime postedAt;
    private LocalDate deadline;
    private Boolean isActive;
    private Boolean isApproved;
    private List<SkillDTO> skills;
    private List<JobCategory> categories;
}
