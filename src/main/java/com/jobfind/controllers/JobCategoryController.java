package com.jobfind.controllers;

import com.jobfind.dto.response.JobCategoryResponse;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.models.JobCategory;
import com.jobfind.services.IJobCatrgoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobCategory")
@RequiredArgsConstructor
public class JobCategoryController {
    private final IJobCatrgoryService jobCategoryServiceImpl;

    @GetMapping("/all")
    public ResponseEntity<List<JobCategoryResponse>> getAllJobCategories() {
        return ResponseEntity.ok(jobCategoryServiceImpl.getAllJobCategories());
    }

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addJobCategory(String jobCategoryName) {
        jobCategoryServiceImpl.addJobCategory(jobCategoryName);
        return ResponseEntity.ok(new SuccessResponse("Job Category added successfully"));
    }

    @DeleteMapping("/delete/{jobCategoryId}")
    public ResponseEntity<SuccessResponse> deleteJobCategory(@PathVariable Integer jobCategoryId) {
        jobCategoryServiceImpl.deleteJobCategory(jobCategoryId);
        return ResponseEntity.ok(new SuccessResponse("JobCategory deleted successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessResponse> updateJobCategory(
            @RequestParam Integer jobCategoryId,
            @RequestParam String jobCategoryName
    ) {
        jobCategoryServiceImpl.updateJobCategory(jobCategoryId, jobCategoryName);
        return ResponseEntity.ok(new SuccessResponse("JobCategory updated successfully"));
    }
}
