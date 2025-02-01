package com.jobfind.services.impl;

import com.jobfind.dto.dto.WorkExperienceDTO;
import com.jobfind.dto.response.JobSeekerProfileResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.Skill;
import com.jobfind.populators.SkillPopulator;
import com.jobfind.repositories.JobSeekerProfileRepository;
import com.jobfind.repositories.WorkExperienceRepository;
import com.jobfind.services.IJobSeekerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobSeekerProfileServiceImpl implements IJobSeekerProfileService {
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final SkillPopulator skillPopulator;

    @Override
    public JobSeekerProfileResponse getProfileByUserId(Integer userId) {
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BadRequestException("JobSeekerProfile not found"));

        List<Skill> profileSkills = jobSeekerProfile.getSkills();

        List<WorkExperienceDTO> workExperiences = workExperienceRepository.findByUser_UserId(userId).stream()
                .map(workExperience -> WorkExperienceDTO.builder()
                        .jobType(workExperience.getJobType())
                        .description(workExperience.getDescription())
                        .startDate(workExperience.getStartDate().toString())
                        .endDate(workExperience.getEndDate().toString())
                        .companyName(workExperience.getCompany().getCompanyName())
                        .jobTitle(workExperience.getJobPosition().getName())
                        .skills(workExperience.getSkills().stream().map(skillPopulator::convertToUserDTO).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());


        return JobSeekerProfileResponse.builder()
                .workExperiences(workExperiences)
                .skills(profileSkills.stream().map(skillPopulator::convertToUserDTO).collect(Collectors.toList()))
                .firstName(jobSeekerProfile.getFirstName())
                .lastName(jobSeekerProfile.getLastName())
                .email(jobSeekerProfile.getUser().getEmail())
                .phone(jobSeekerProfile.getUser().getPhone())
                .resumePath(jobSeekerProfile.getResumePath())
                .build();
    }
}
