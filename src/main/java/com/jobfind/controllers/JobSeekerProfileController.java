package com.jobfind.controllers;

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

@RestController
@RequestMapping("/jobseeker")
public class JobSeekerProfileController {
    @Autowired
    private IJobSeekerProfileService jobSeekerProfileServiceImpl;

    @GetMapping("/getProfileByUserId")
    public ResponseEntity<JobSeekerProfileResponse> getJobSeekerProfile(@RequestParam Integer userId) {
        return ResponseEntity.ok(jobSeekerProfileServiceImpl.getProfileByUserId(userId));
    }

    @PostMapping("/addWorkExperience")
    public ResponseEntity<SuccessResponse> createWorkExperience(@RequestParam Integer userId, @Valid @RequestBody CreateWorkExperienceRequest createWorkExperienceRequest, BindingResult result) {
        jobSeekerProfileServiceImpl.addWorkExperience(userId, createWorkExperienceRequest, result);
        return ResponseEntity.ok(new SuccessResponse("Work Experience created successfully"));
    }

    @PostMapping("/updateWorkExperience")
    public ResponseEntity<SuccessResponse> updateWorkExperience(@RequestParam Integer userId, @Valid @RequestBody UpdateWorkExperienceRequest updateWorkExperienceRequest, BindingResult result) {
        jobSeekerProfileServiceImpl.updateWorkExperience(userId, updateWorkExperienceRequest, result);
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
}
