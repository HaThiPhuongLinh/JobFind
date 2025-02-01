package com.jobfind.controllers;

import com.jobfind.dto.response.JobSeekerProfileResponse;
import com.jobfind.services.IJobSeekerProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobseeker")
public class JobSeekerProfileController {

    @Autowired
    private IJobSeekerProfileService jobSeekerProfileServiceImpl;

    @GetMapping("/getProfileByUserId")
    public ResponseEntity<JobSeekerProfileResponse> getJobSeekerProfile(@RequestParam Integer userId) {
        return ResponseEntity.ok(jobSeekerProfileServiceImpl.getProfileByUserId(userId));
    }
}
