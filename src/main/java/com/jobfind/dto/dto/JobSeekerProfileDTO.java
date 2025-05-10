package com.jobfind.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class JobSeekerProfileDTO {
    private Integer profileId;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String address;
    private String avatar;
    private String title;
    private String email;
    private String phone;
    private LocalDate birthDay;
    private List<SkillDTO> skills;
    private List<ResumeDTO> resumeList;
    private List<WorkExperienceDTO> workExperiences;
}
