package com.jobfind.controllers;

import com.jobfind.dto.request.ResumeRequest;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.IResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final IResumeService resumeServiceImpl;

    @PostMapping("create/{profileId}")
    public ResponseEntity<SuccessResponse> createResume(@PathVariable Integer profileId, @Valid @ModelAttribute ResumeRequest request, BindingResult result) throws IOException {
        resumeServiceImpl.createResume(profileId, request, result);
        return ResponseEntity.ok(new SuccessResponse("Create resume successfully"));
    }

    @DeleteMapping("delete/{resumeId}")
    public ResponseEntity<SuccessResponse> deleteResume(@PathVariable Integer resumeId) {
        resumeServiceImpl.deleteResume(resumeId);
        return ResponseEntity.ok(new SuccessResponse("Delete resume successfully"));
    }
}
