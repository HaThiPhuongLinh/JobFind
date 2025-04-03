package com.jobfind.controllers;

import com.jobfind.dto.dto.JobDTO;
import com.jobfind.dto.request.CreateJobRequest;
import com.jobfind.dto.request.UpdateJobRequest;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.IJobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private IJobService jobServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createJob(@Valid @RequestBody CreateJobRequest request, BindingResult bindingResult) {
        jobServiceImpl.createJob(request, bindingResult);
        return ResponseEntity.ok(new SuccessResponse("Job created successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateJob(@Valid @RequestBody UpdateJobRequest request, BindingResult bindingResult) {
        jobServiceImpl.updateJob(request, bindingResult);
        return ResponseEntity.ok("Job updated successfully.");
    }

    @DeleteMapping("delete/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable Integer jobId) {
        jobServiceImpl.deleteJob(jobId);
        return ResponseEntity.ok("Job deleted successfully.");
    }

    @GetMapping("/searchJobs")
    public ResponseEntity<List<JobDTO>> searchJobs(@RequestParam(required = false) String keyword, @RequestParam(required = false) String location, @RequestParam(required = false) Integer jobCategoryId) {
        List<JobDTO> jobs = jobServiceImpl.searchJobs(keyword, location, jobCategoryId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobDTO>> getJobsByCompanyId(@PathVariable Integer companyId) {
        return ResponseEntity.ok(jobServiceImpl.getJobsByCompanyId(companyId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<JobDTO>> getJobsByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(jobServiceImpl.getJobsByCategory(categoryId));
    }

    @GetMapping ("/getJobById/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Integer id) {
        JobDTO job = jobServiceImpl.getJobByID(id);
        return ResponseEntity.ok(job);
    }

    @PutMapping("/approve/{jobId}")
    public ResponseEntity<String> approveJob(@PathVariable Integer jobId) {
        jobServiceImpl.approveJob(jobId);
        return ResponseEntity.ok("Job approved successfully.");
    }
}