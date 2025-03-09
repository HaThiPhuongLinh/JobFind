package com.jobfind.controllers;

import com.jobfind.dto.response.SavedJobResponse;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.ISavedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/savedJob")
@RequiredArgsConstructor
public class SavedJobController {
    private final ISavedJobService savedJobServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<SuccessResponse> saveJob(Integer jobId, Integer userId) {
        savedJobServiceImpl.saveJob(jobId, userId);
        return ResponseEntity.ok(new SuccessResponse("Job saved successfully"));
    }

    @PostMapping("/unsave")
    public ResponseEntity<SuccessResponse> unsaveJob(Integer jobId, Integer userId) {
        savedJobServiceImpl.unsaveJob(jobId, userId);
        return ResponseEntity.ok(new SuccessResponse("Job unsaved successfully"));
    }

    @GetMapping("/list")
    public ResponseEntity<List<SavedJobResponse>> getListSavedJobs(Integer userId) {
        return ResponseEntity.ok(savedJobServiceImpl.getListSavedJobs(userId));
    }
}
