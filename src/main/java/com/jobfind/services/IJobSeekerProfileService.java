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
    JobSeekerProfileDTO getProfileById(Integer jobSeekerId);
    void addWorkExperience(Integer jobSeekerId, CreateWorkExperienceRequest request, BindingResult result);
    void updateWorkExperience(Integer jobSeekerId, UpdateWorkExperienceRequest request, BindingResult result);
    void addSkills(SkillRequest createSkillsRequest, BindingResult result);
    void updateSkills(SkillRequest skillRequest, BindingResult bindingResult);
    List<JobSeekerProfileDTO> searchJobSeekers(String keyword, List<Integer> categoryIds, String location, Integer companyId);
    List<JobSeekerProfileDTO> findJobSeekersByCompanyIndustry(Integer companyId);
}