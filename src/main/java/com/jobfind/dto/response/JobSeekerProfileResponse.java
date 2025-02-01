package com.jobfind.dto.response;

import com.jobfind.dto.dto.SkillDTO;
import com.jobfind.dto.dto.WorkExperienceDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobSeekerProfileResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String resumePath;
    private List<WorkExperienceDTO> workExperiences;
    private List<SkillDTO> skills;
}