package com.jobfind.dto.dto;

import com.jobfind.models.JobCategory;
import com.jobfind.models.enums.JobType;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceDTO {
    private String jobTitle;
    private String companyName;
    private String logo;
    private String description;
    private JobType jobType;
    private String startDate;
    private String endDate;
    private List<SkillDTO> skills;
    private List<JobCategory> categories;
}