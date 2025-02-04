package com.jobfind.services;

import com.jobfind.dto.request.UpdateWorkExperienceRequest;
import com.jobfind.dto.response.JobSeekerProfileResponse;
import org.springframework.validation.BindingResult;

public interface IJobSeekerProfileService {
    JobSeekerProfileResponse getProfileByUserId(Integer userId);
    JobSeekerProfileResponse updateWorkExperience(Integer userId, UpdateWorkExperienceRequest request, BindingResult result);
}