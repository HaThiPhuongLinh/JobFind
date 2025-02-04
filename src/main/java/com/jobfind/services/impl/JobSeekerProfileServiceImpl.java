package com.jobfind.services.impl;

import com.jobfind.dto.dto.WorkExperienceDTO;
import com.jobfind.dto.request.UpdateWorkExperienceRequest;
import com.jobfind.dto.response.JobSeekerProfileResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.Skill;
import com.jobfind.models.WorkExperience;
import com.jobfind.populators.SkillPopulator;
import com.jobfind.repositories.*;
import com.jobfind.services.IJobSeekerProfileService;
import com.jobfind.utils.ValidateField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobSeekerProfileServiceImpl implements IJobSeekerProfileService {
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final CompanyRepository companyRepository;
    private final JobPositionRepository jobPositionRepository;
    private final SkillRepository skillRepository;
    private final SkillPopulator skillPopulator;
    private final ValidateField validateField;

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
                        .skills(workExperience.getSkills().stream().map(skillPopulator::convertToSkillDTO).collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());


        return JobSeekerProfileResponse.builder()
                .workExperiences(workExperiences)
                .skills(profileSkills.stream().map(skillPopulator::convertToSkillDTO).collect(Collectors.toList()))
                .firstName(jobSeekerProfile.getFirstName())
                .lastName(jobSeekerProfile.getLastName())
                .email(jobSeekerProfile.getUser().getEmail())
                .phone(jobSeekerProfile.getUser().getPhone())
                .resumePath(jobSeekerProfile.getResumePath())
                .build();
    }

    @Override
    public JobSeekerProfileResponse updateWorkExperience(Integer userId, UpdateWorkExperienceRequest request, BindingResult result) {
        JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findByUser_UserId(userId).orElseThrow(() -> new BadRequestException("JobSeekerProfile not found"));

        Map<String, String> errors = validateField.getErrors(result);

        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        List<WorkExperience> existingExperiences = workExperienceRepository.findByUser_UserId(userId);
        Map<Integer, WorkExperience> experienceMap = existingExperiences.stream().collect(Collectors.toMap(WorkExperience::getWorkExperienceId, exp -> exp));


        Integer workExpId = request.getWorkExperienceId();

        if (workExpId == null) {
            throw new BadRequestException("WorkExperience ID is required for update");
        }

        WorkExperience workExperience = experienceMap.get(workExpId);
        if (workExperience == null) {
            throw new BadRequestException("WorkExperience not found with ID: " + workExpId);
        }

        workExperience.setJobType(request.getJobType());
        workExperience.setDescription(request.getDescription());
        workExperience.setStartDate(request.getStartDate());
        workExperience.setEndDate(request.getEndDate());

        workExperience.setCompany(companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new BadRequestException("Company not found with ID: " + request.getCompanyId())));

        workExperience.setJobPosition(jobPositionRepository.findById(request.getJobPositionId()).orElseThrow(() -> new BadRequestException("Job position not found with ID: " + request.getJobPositionId())));

        List<Skill> workExpSkills = request.getSkills().stream().map(skillId -> skillRepository.findById(skillId).orElseThrow(() -> new BadRequestException("Skill not found with ID: " + skillId))).collect(Collectors.toList());

        workExperience.setSkills(workExpSkills);
        workExperience.setUser(jobSeekerProfile.getUser());


        workExperienceRepository.save(workExperience);

        jobSeekerProfileRepository.save(jobSeekerProfile);

        return JobSeekerProfileResponse.builder()
                .workExperiences(existingExperiences.stream().map(exp -> WorkExperienceDTO.builder().jobType(exp.getJobType()).description(exp.getDescription()).startDate(exp.getStartDate().toString()).endDate(exp.getEndDate().toString()).companyName(exp.getCompany().getCompanyName()).jobTitle(exp.getJobPosition().getName()).skills(exp.getSkills().stream().map(skillPopulator::convertToSkillDTO).collect(Collectors.toList())).build()).collect(Collectors.toList()))
                .skills(jobSeekerProfile.getSkills().stream().map(skillPopulator::convertToSkillDTO).collect(Collectors.toList()))
                .firstName(jobSeekerProfile.getFirstName())
                .lastName(jobSeekerProfile.getLastName())
                .email(jobSeekerProfile.getUser().getEmail())
                .phone(jobSeekerProfile.getUser().getPhone())
                .resumePath(jobSeekerProfile.getResumePath())
                .build();
    }
}
