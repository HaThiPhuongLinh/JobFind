package com.jobfind.controllers;

import com.jobfind.dto.request.CreateWorkExperienceRequest;
import com.jobfind.dto.request.SkillRequest;
import com.jobfind.dto.request.UpdateWorkExperienceRequest;
import com.jobfind.dto.response.JobSeekerProfileResponse;
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

    @PostMapping("/createWorkExperience")
    public ResponseEntity<JobSeekerProfileResponse> createWorkExperience(@RequestParam Integer userId, @Valid @RequestBody CreateWorkExperienceRequest createWorkExperienceRequest, BindingResult result) {
        return ResponseEntity.ok(jobSeekerProfileServiceImpl.createWorkExperience(userId, createWorkExperienceRequest, result));
    }

    @PostMapping("/updateWorkExperience")
    public ResponseEntity<JobSeekerProfileResponse> updateWorkExperience(@RequestParam Integer userId, @Valid @RequestBody UpdateWorkExperienceRequest updateWorkExperienceRequest, BindingResult result) {
        return ResponseEntity.ok(jobSeekerProfileServiceImpl.updateWorkExperience(userId, updateWorkExperienceRequest, result));
    }

    @PostMapping("/createSkill")
    public ResponseEntity<JobSeekerProfileResponse> createSkills(@Valid @RequestBody SkillRequest createSkillsRequest, BindingResult result) {
        return ResponseEntity.ok(jobSeekerProfileServiceImpl.createSkills(createSkillsRequest, result));
    }

    @PostMapping("/updateSkill")
    public ResponseEntity<JobSeekerProfileResponse> updateSkills(@Valid @RequestBody SkillRequest skillRequest, BindingResult bindingResult) {
        return ResponseEntity.ok(jobSeekerProfileServiceImpl.updateSkills(skillRequest, bindingResult));
    }
}
