package com.jobfind.controllers;

import com.jobfind.dto.dto.JobSeekerProfileDTO;
import com.jobfind.dto.request.CreateWorkExperienceRequest;
import com.jobfind.dto.request.SkillRequest;
import com.jobfind.dto.request.UpdateWorkExperienceRequest;
import com.jobfind.dto.response.JobSeekerProfileResponse;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.IJobSeekerProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobseeker")
public class JobSeekerProfileController {
    @Autowired
    private IJobSeekerProfileService jobSeekerProfileServiceImpl;

    @GetMapping("/all")
    public ResponseEntity<List<JobSeekerProfileDTO>> getAllJobSeekerProfiles() {
        return ResponseEntity.ok(jobSeekerProfileServiceImpl.getAllJobSeekerProfiles());
    }

    @GetMapping("/getProfileByUserId")
    public ResponseEntity<JobSeekerProfileResponse> getJobSeekerProfile(@RequestParam Integer userId) {
        return ResponseEntity.ok(jobSeekerProfileServiceImpl.getProfileByUserId(userId));
    }

    @GetMapping("/getProfileById")
    public ResponseEntity<JobSeekerProfileDTO> getJobSeekerProfileById(@RequestParam Integer jobSeekerId) {
        return ResponseEntity.ok(jobSeekerProfileServiceImpl.getProfileById(jobSeekerId));
    }

    @PostMapping("/addWorkExperience")
    public ResponseEntity<SuccessResponse> createWorkExperience(@RequestParam Integer jobSeekerId, @Valid @RequestBody CreateWorkExperienceRequest createWorkExperienceRequest, BindingResult result) {
        jobSeekerProfileServiceImpl.addWorkExperience(jobSeekerId, createWorkExperienceRequest, result);
        return ResponseEntity.ok(new SuccessResponse("Work Experience created successfully"));
    }

    @PostMapping("/updateWorkExperience")
    public ResponseEntity<SuccessResponse> updateWorkExperience(@RequestParam Integer jobSeekerId, @Valid @RequestBody UpdateWorkExperienceRequest updateWorkExperienceRequest, BindingResult result) {
        jobSeekerProfileServiceImpl.updateWorkExperience(jobSeekerId, updateWorkExperienceRequest, result);
        return ResponseEntity.ok(new SuccessResponse("Work Experience updated successfully"));
    }

    @PostMapping("/addSkill")
    public ResponseEntity<SuccessResponse> createSkills(@Valid @RequestBody SkillRequest createSkillsRequest, BindingResult result) {
        jobSeekerProfileServiceImpl.addSkills(createSkillsRequest, result);
        return ResponseEntity.ok(new SuccessResponse("Skill created successfully"));
    }

    @PostMapping("/updateSkill")
    public ResponseEntity<SuccessResponse> updateSkills(@Valid @RequestBody SkillRequest skillRequest, BindingResult bindingResult) {
        jobSeekerProfileServiceImpl.updateSkills(skillRequest, bindingResult);
        return ResponseEntity.ok(new SuccessResponse("Skill updated successfully"));
    }

    @GetMapping("/search-jobseekers")
    public ResponseEntity<List<JobSeekerProfileDTO>> searchJobSeekers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<Integer> categoryIds,
            @RequestParam(required = false) List<String> location,
            @RequestParam(required = false) Integer companyId) {
        List<JobSeekerProfileDTO> jobSeekers = jobSeekerProfileServiceImpl
                .searchJobSeekers(keyword, categoryIds, location, companyId);

        return ResponseEntity.ok(jobSeekers);
    }

    @GetMapping("/find-jobseekers-by-company-industry")
    public ResponseEntity<List<JobSeekerProfileDTO>> findJobSeekersByCompanyIndustry(@RequestParam Integer companyId) {
        List<JobSeekerProfileDTO> jobSeekers = jobSeekerProfileServiceImpl.findJobSeekersByCompanyIndustry(companyId);
        return ResponseEntity.ok(jobSeekers);
    }
}
