package com.jobfind.controllers;

import com.jobfind.dto.dto.JobDTO;
import com.jobfind.dto.request.CreateJobRequest;
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

    @GetMapping("/searchJobs")
    public ResponseEntity<List<JobDTO>> searchJobs(@RequestParam(required = false) String keyword, @RequestParam(required = false) String location) {
        List<JobDTO> jobs = jobServiceImpl.searchJobs(keyword, location);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping ("/getJobById/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Integer id) {
        JobDTO job = jobServiceImpl.getJobByID(id);
        return ResponseEntity.ok(job);
    }
}