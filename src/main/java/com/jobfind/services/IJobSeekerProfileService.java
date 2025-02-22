package com.jobfind.services;

import com.jobfind.dto.dto.JobSeekerProfileDTO;
import com.jobfind.dto.request.CreateWorkExperienceRequest;
import com.jobfind.dto.request.SkillRequest;
import com.jobfind.dto.request.UpdateWorkExperienceRequest;
import com.jobfind.dto.response.JobSeekerProfileResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IJobSeekerProfileService {
    JobSeekerProfileResponse getProfileByUserId(Integer userId);
    void addWorkExperience(Integer userId, CreateWorkExperienceRequest request, BindingResult result);
    void updateWorkExperience(Integer userId, UpdateWorkExperienceRequest request, BindingResult result);
    void addSkills(SkillRequest createSkillsRequest, BindingResult result);
    void updateSkills(SkillRequest skillRequest, BindingResult bindingResult);
    List<JobSeekerProfileDTO> searchJobSeekers(String keyword, Integer jobCategoryId);
}