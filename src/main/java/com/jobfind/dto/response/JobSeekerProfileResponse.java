package com.jobfind.dto.response;

import com.jobfind.dto.dto.SkillDTO;
import com.jobfind.dto.dto.WorkExperienceDTO;
import com.jobfind.models.Resume;
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
    private List<Resume> resumeList;
    private List<WorkExperienceDTO> workExperiences;
    private List<SkillDTO> skills;
}