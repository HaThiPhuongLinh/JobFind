package com.jobfind.services;

import com.jobfind.dto.request.CreateWorkExperienceRequest;
import com.jobfind.dto.request.SkillRequest;
import com.jobfind.dto.request.UpdateWorkExperienceRequest;
import com.jobfind.dto.response.JobSeekerProfileResponse;
import org.springframework.validation.BindingResult;

public interface IJobSeekerProfileService {
    JobSeekerProfileResponse getProfileByUserId(Integer userId);
    JobSeekerProfileResponse createWorkExperience(Integer userId, CreateWorkExperienceRequest request, BindingResult result);
    JobSeekerProfileResponse updateWorkExperience(Integer userId, UpdateWorkExperienceRequest request, BindingResult result);
    JobSeekerProfileResponse createSkills(SkillRequest createSkillsRequest, BindingResult result);
    JobSeekerProfileResponse updateSkills(SkillRequest skillRequest, BindingResult bindingResult);
}