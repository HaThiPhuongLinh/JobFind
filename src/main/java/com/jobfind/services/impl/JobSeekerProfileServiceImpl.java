package com.jobfind.services.impl;

import com.jobfind.dto.dto.WorkExperienceDTO;
import com.jobfind.dto.request.CreateWorkExperienceRequest;
import com.jobfind.dto.request.SkillRequest;
import com.jobfind.dto.request.UpdateWorkExperienceRequest;
import com.jobfind.dto.response.JobSeekerProfileResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.Skill;
import com.jobfind.models.WorkExperience;
import com.jobfind.converters.SkillConverter;
import com.jobfind.converters.WorkExperienceConverter;
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
    private final SkillConverter skillConverter;
    private final WorkExperienceConverter workExperienceConverter;
    private final ValidateField validateField;

    private JobSeekerProfile getJobSeekerProfile(Integer userId) {
        return jobSeekerProfileRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new BadRequestException("JobSeekerProfile not found"));
    }

    private List<Skill> getSkillsByIds(List<Integer> skillIds) {
        return skillIds.stream()
                .map(skillId -> skillRepository.findById(skillId)
                        .orElseThrow(() -> new BadRequestException("Skill not found")))
                .collect(Collectors.toList());
    }

    @Override
    public JobSeekerProfileResponse getProfileByUserId(Integer userId) {
        JobSeekerProfile jobSeekerProfile = getJobSeekerProfile(userId);

        List<WorkExperienceDTO> workExperiences = workExperienceRepository.findByUser_UserId(userId).stream()
                .map(workExperienceConverter::convertToWorkExperienceDTO)
                .collect(Collectors.toList());

        return JobSeekerProfileResponse.builder()
                .resumeList(jobSeekerProfile.getResumes())
                .workExperiences(workExperiences)
                .skills(jobSeekerProfile.getSkills().stream()
                        .map(skillConverter::convertToSkillDTO)
                        .collect(Collectors.toList()))
                .firstName(jobSeekerProfile.getFirstName())
                .lastName(jobSeekerProfile.getLastName())
                .email(jobSeekerProfile.getUser().getEmail())
                .phone(jobSeekerProfile.getUser().getPhone())
                .build();
    }

    @Override
    public JobSeekerProfileResponse createWorkExperience(Integer userId, CreateWorkExperienceRequest request, BindingResult result) {
        JobSeekerProfile jobSeekerProfile = getJobSeekerProfile(userId);

        Map<String, String> errors = validateField.getErrors(result);
        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        boolean exists = workExperienceRepository.existsByUserAndCompanyAndJobPosition(
                jobSeekerProfile.getUser(),
                companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new BadRequestException("Company not found")),
                jobPositionRepository.findById(request.getJobPositionId()).orElseThrow(() -> new BadRequestException("Job position not found"))
        );

        if (exists) {
            throw new BadRequestException("Work experience already exists.");
        }

        WorkExperience workExperience = new WorkExperience();
        workExperience.setJobType(request.getJobType());
        workExperience.setDescription(request.getDescription());
        workExperience.setStartDate(request.getStartDate());
        workExperience.setEndDate(request.getEndDate());

        workExperience.setCompany(companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new BadRequestException("Company not found")));

        workExperience.setJobPosition(jobPositionRepository.findById(request.getJobPositionId())
                .orElseThrow(() -> new BadRequestException("Job position not found")));

        workExperience.setSkills(getSkillsByIds(request.getSkills()));
        workExperience.setUser(jobSeekerProfile.getUser());

        workExperienceRepository.save(workExperience);

        return JobSeekerProfileResponse.builder()
                .resumeList(jobSeekerProfile.getResumes())
                .workExperiences(List.of(workExperienceConverter.convertToWorkExperienceDTO(workExperience)))
                .skills(jobSeekerProfile.getSkills().stream()
                        .map(skillConverter::convertToSkillDTO)
                        .collect(Collectors.toList()))
                .firstName(jobSeekerProfile.getFirstName())
                .lastName(jobSeekerProfile.getLastName())
                .email(jobSeekerProfile.getUser().getEmail())
                .phone(jobSeekerProfile.getUser().getPhone())
                .build();
    }

    @Override
    public JobSeekerProfileResponse updateWorkExperience(Integer userId, UpdateWorkExperienceRequest request, BindingResult result) {
        JobSeekerProfile jobSeekerProfile = getJobSeekerProfile(userId);

        Map<String, String> errors = validateField.getErrors(result);
        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        WorkExperience workExperience = workExperienceRepository.findById(request.getWorkExperienceId())
                .orElseThrow(() -> new BadRequestException("WorkExperience not found with ID: " + request.getWorkExperienceId()));

        workExperience.setJobType(request.getJobType());
        workExperience.setDescription(request.getDescription());
        workExperience.setStartDate(request.getStartDate());
        workExperience.setEndDate(request.getEndDate());

        workExperience.setCompany(companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new BadRequestException("Company not found")));

        workExperience.setJobPosition(jobPositionRepository.findById(request.getJobPositionId())
                .orElseThrow(() -> new BadRequestException("Job position not found")));

        workExperience.setSkills(getSkillsByIds(request.getSkills()));
        workExperience.setUser(jobSeekerProfile.getUser());

        workExperienceRepository.save(workExperience);

        return JobSeekerProfileResponse.builder()
                .resumeList(jobSeekerProfile.getResumes())
                .workExperiences(workExperienceRepository.findByUser_UserId(userId).stream()
                        .map(workExperienceConverter::convertToWorkExperienceDTO)
                        .collect(Collectors.toList()))
                .skills(jobSeekerProfile.getSkills().stream()
                        .map(skillConverter::convertToSkillDTO)
                        .collect(Collectors.toList()))
                .firstName(jobSeekerProfile.getFirstName())
                .lastName(jobSeekerProfile.getLastName())
                .email(jobSeekerProfile.getUser().getEmail())
                .phone(jobSeekerProfile.getUser().getPhone())
                .build();
    }

    @Override
    public JobSeekerProfileResponse createSkills(SkillRequest createSkillsRequest, BindingResult result) {
        Map<String, String> errors = validateField.getErrors(result);
        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        JobSeekerProfile profile = getJobSeekerProfile(createSkillsRequest.getProfileId());
        List<Skill> existingSkills = profile.getSkills();
        List<Skill> newSkills = getSkillsByIds(createSkillsRequest.getSkills());

        for (Skill skill : newSkills) {
            if (!existingSkills.contains(skill)) {
                existingSkills.add(skill);
            }
        }

        jobSeekerProfileRepository.save(profile);

        return JobSeekerProfileResponse.builder()
                .skills(profile.getSkills().stream()
                        .map(skillConverter::convertToSkillDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public JobSeekerProfileResponse updateSkills(SkillRequest skillRequest, BindingResult bindingResult) {
        Map<String, String> errors = validateField.getErrors(bindingResult);
        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        JobSeekerProfile profile = getJobSeekerProfile(skillRequest.getProfileId());

        List<Skill> skills = getSkillsByIds(skillRequest.getSkills());
        profile.setSkills(skills);

        jobSeekerProfileRepository.save(profile);

        return JobSeekerProfileResponse.builder()
                .skills(profile.getSkills().stream()
                        .map(skillConverter::convertToSkillDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
