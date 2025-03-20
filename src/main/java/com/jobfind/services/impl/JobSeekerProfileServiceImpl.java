package com.jobfind.services.impl;

import com.jobfind.converters.JobSeekerProfileConverter;
import com.jobfind.converters.ResumeConverter;
import com.jobfind.dto.dto.JobSeekerProfileDTO;
import com.jobfind.dto.dto.WorkExperienceDTO;
import com.jobfind.dto.request.CreateWorkExperienceRequest;
import com.jobfind.dto.request.SkillRequest;
import com.jobfind.dto.request.UpdateWorkExperienceRequest;
import com.jobfind.dto.response.JobSeekerProfileResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.JobCategory;
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

import java.util.ArrayList;
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
    private final JobCategoryRepository jobCategoryRepository;
    private final SkillRepository skillRepository;
    private final SkillConverter skillConverter;
    private final WorkExperienceConverter workExperienceConverter;
    private final JobSeekerProfileConverter jobSeekerProfileConverter;
    private final ResumeConverter resumeConverter;
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

    private List<JobCategory> getJobCategoriesByIds(List<Integer> categoryIds) {
        return categoryIds.stream()
                .map(categoryId -> jobCategoryRepository.findById(categoryId)
                        .orElseThrow(() -> new BadRequestException("Job Category not found")))
                .collect(Collectors.toList());
    }

    @Override
    public JobSeekerProfileResponse getProfileByUserId(Integer userId) {
        JobSeekerProfile jobSeekerProfile = getJobSeekerProfile(userId);

        List<WorkExperienceDTO> workExperiences = jobSeekerProfile.getWorkExperiences().stream()
                .map(workExperienceConverter::convertToWorkExperienceDTO)
                .collect(Collectors.toList());

        return JobSeekerProfileResponse.builder()
                .resumeList(jobSeekerProfile.getResumes().stream()
                        .map(resumeConverter::convertToResumeDTO)
                        .collect(Collectors.toList()))
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
    public void addWorkExperience(Integer userId, CreateWorkExperienceRequest request, BindingResult result) {
        JobSeekerProfile jobSeekerProfile = getJobSeekerProfile(userId);

        Map<String, String> errors = validateField.getErrors(result);
        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        boolean exists = workExperienceRepository.existsByJobSeekerProfileAndCompanyAndJobPosition(
                jobSeekerProfile,
                companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new BadRequestException("Company not found")),
                jobPositionRepository.findById(request.getJobPositionId()).orElseThrow(() -> new BadRequestException("Job position not found"))
        );

        if (exists) {
            throw new BadRequestException("Work experience already exists.");
        }

        WorkExperience workExperience = WorkExperience.builder()
                .jobType(request.getJobType())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .company(companyRepository.findById(request.getCompanyId())
                        .orElseThrow(() -> new BadRequestException("Company not found")))
                .jobPosition(jobPositionRepository.findById(request.getJobPositionId())
                        .orElseThrow(() -> new BadRequestException("Job position not found")))
                .skills(getSkillsByIds(request.getSkills()))
                .categories(getJobCategoriesByIds(request.getCategories()))
                .jobSeekerProfile(jobSeekerProfile)
                .build();

        workExperienceRepository.save(workExperience);
    }

    @Override
    public void updateWorkExperience(Integer userId, UpdateWorkExperienceRequest request, BindingResult result) {
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
        workExperience.setCategories(getJobCategoriesByIds(request.getCategories()));
        workExperience.setJobSeekerProfile(jobSeekerProfile);

        workExperienceRepository.save(workExperience);
    }

    @Override
    public void addSkills(SkillRequest createSkillsRequest, BindingResult result) {
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
    }

    @Override
    public void updateSkills(SkillRequest skillRequest, BindingResult bindingResult) {
        Map<String, String> errors = validateField.getErrors(bindingResult);
        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        JobSeekerProfile profile = getJobSeekerProfile(skillRequest.getProfileId());

        List<Skill> skills = getSkillsByIds(skillRequest.getSkills());
        profile.setSkills(skills);

        jobSeekerProfileRepository.save(profile);
    }

    @Override
    public List<JobSeekerProfileDTO> searchJobSeekers(String keyword, Integer jobCategoryId) {
        if (jobCategoryId == null) {
            return new ArrayList<>();
        }
        return jobSeekerProfileRepository.searchJobSeekers(keyword, jobCategoryId).stream()
                .map(jobSeekerProfileConverter::convertToJobSeekerProfileDTO)
                .collect(Collectors.toList());
    }
}
