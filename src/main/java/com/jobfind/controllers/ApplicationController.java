package com.jobfind.controllers;

import com.jobfind.dto.request.ApplicationRequest;
import com.jobfind.dto.response.ApplicationStatusResponse;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.impl.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("updateStatus/{applicationId}")
    public ResponseEntity<SuccessResponse> updateApplicationStatus(@PathVariable Integer applicationId, @RequestParam String status) {
        applicationServiceImpl.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok(new SuccessResponse("Application status updated successfully"));
    }
}
