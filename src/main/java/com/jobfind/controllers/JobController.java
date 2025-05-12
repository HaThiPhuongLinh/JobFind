package com.jobfind.controllers;

import com.jobfind.dto.dto.JobDTO;
import com.jobfind.dto.request.CreateJobRequest;
import com.jobfind.dto.request.RejectJobRequest;
import com.jobfind.dto.request.UpdateJobRequest;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.models.JobPosition;
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

    @GetMapping("/all")
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobs = jobServiceImpl.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse> createJob(@Valid @RequestBody CreateJobRequest request, BindingResult bindingResult) {
        jobServiceImpl.createJob(request, bindingResult);
        return ResponseEntity.ok(new SuccessResponse("Job created successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessResponse> updateJob(@Valid @RequestBody UpdateJobRequest request, BindingResult bindingResult) {
        jobServiceImpl.updateJob(request, bindingResult);
        return ResponseEntity.ok(new SuccessResponse("Job updated successfully"));
    }

    @DeleteMapping("delete/{jobId}")
    public ResponseEntity<SuccessResponse> deleteJob(@PathVariable Integer jobId) {
        jobServiceImpl.deleteJob(jobId);
        return ResponseEntity.ok(new SuccessResponse("Job deleted successfully"));
    }

    @GetMapping("/searchJobs")
    public ResponseEntity<List<JobDTO>> searchJobs(@RequestParam(required = false) String keyword, @RequestParam(required = false) List<String> location, @RequestParam(required = false) List<Integer> jobCategoryId) {
        List<JobDTO> jobs = jobServiceImpl.searchJobs(keyword, location, jobCategoryId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<JobDTO>> getJobsByCompanyId(@PathVariable Integer companyId,
                                                           @RequestParam Integer id) {
        return ResponseEntity.ok(jobServiceImpl.getJobsByCompanyId(companyId, id));
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
    public ResponseEntity<SuccessResponse> approveJob(@PathVariable Integer jobId) {
        jobServiceImpl.approveJob(jobId);
        return ResponseEntity.ok(new SuccessResponse("Job approved successfully"));
    }

    @PutMapping("/reject")
    public ResponseEntity<SuccessResponse> rejectJob(@RequestBody RejectJobRequest request) {
        jobServiceImpl.rejectJob(request);
        return ResponseEntity.ok(new SuccessResponse("Job rejected successfully"));
    }

    @GetMapping("/proposedJobs/{jobSeekerId}")
    public ResponseEntity<List<JobDTO>> getProposedJobs(@PathVariable Integer jobSeekerId) {
        List<JobDTO> jobs = jobServiceImpl.getProposedJobs(jobSeekerId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/jobPosition")
    public ResponseEntity<List<JobPosition>> getAllJobPosition() {
        List<JobPosition> jobPositions = jobServiceImpl.getAllJobPosition();
        return ResponseEntity.ok(jobPositions);
    }
}