package com.jobfind.dto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class JobSeekerProfileDTO {
    private Integer profileId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;
    private List<SkillDTO> skills;
    private List<ResumeDTO> resumeList;
}
