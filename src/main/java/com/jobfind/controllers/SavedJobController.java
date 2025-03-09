package com.jobfind.controllers;

import com.jobfind.dto.response.SavedJobResponse;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.ISavedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savedJob")
@RequiredArgsConstructor
public class SavedJobController {
    private final ISavedJobService savedJobServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<SuccessResponse> saveJob(@RequestParam Integer jobId, @RequestParam Integer jobSeekerProfileId) {
        savedJobServiceImpl.saveJob(jobId, jobSeekerProfileId);
        return ResponseEntity.ok(new SuccessResponse("Job saved successfully"));
    }

    @PostMapping("/unsave")
    public ResponseEntity<SuccessResponse> unsaveJob(@RequestParam Integer jobId, @RequestParam Integer jobSeekerProfileId) {
        savedJobServiceImpl.unsaveJob(jobId, jobSeekerProfileId);
        return ResponseEntity.ok(new SuccessResponse("Job unsaved successfully"));
    }

    @GetMapping("/listSavedJobs")
    public ResponseEntity<List<SavedJobResponse>> getListSavedJobs(@RequestParam Integer jobSeekerProfileId) {
        return ResponseEntity.ok(savedJobServiceImpl.getListSavedJobs(jobSeekerProfileId));
    }
}
