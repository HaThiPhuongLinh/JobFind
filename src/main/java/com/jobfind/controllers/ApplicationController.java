package com.jobfind.controllers;

import com.jobfind.dto.request.ApplicationRequest;
import com.jobfind.dto.response.ApplicationOfJobResponse;
import com.jobfind.dto.response.ApplicationStatusResponse;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.impl.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationServiceImpl applicationServiceImpl;

    @PostMapping("/apply")
    public ResponseEntity<SuccessResponse> applyForJob(@RequestBody ApplicationRequest request) {
        applicationServiceImpl.applyForJob(request);
        return ResponseEntity.ok(new SuccessResponse("Application created successfully"));
    }

    @GetMapping("/{applicationId}/history")
    public ResponseEntity<ApplicationStatusResponse> getApplicationHistory(@PathVariable Integer applicationId) {
        ApplicationStatusResponse history = applicationServiceImpl.getApplicationStatusHistory(applicationId);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/jobSeeker/{jobSeekerId}")
    public ResponseEntity<List<ApplicationStatusResponse>> getApplicationOfJobByJobSeeker(@PathVariable Integer jobSeekerId) {
        List<ApplicationStatusResponse> response = applicationServiceImpl.getApplicationOfJobByJobSeeker(jobSeekerId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("updateStatus/{applicationId}")
    public ResponseEntity<SuccessResponse> updateApplicationStatus(@PathVariable Integer applicationId, @RequestParam String status) {
        applicationServiceImpl.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok(new SuccessResponse("Application status updated successfully"));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationOfJobResponse>> getApplicationOfJob(@PathVariable Integer jobId) {
        List<ApplicationOfJobResponse> response = applicationServiceImpl.getApplicationOfJob(jobId);
        return ResponseEntity.ok(response);
    }
}
