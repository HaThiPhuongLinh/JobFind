package com.jobfind.services;

import com.jobfind.dto.request.ResumeRequest;
import org.springframework.validation.BindingResult;

public interface IResumeService {
    void createResume(Integer profileId, ResumeRequest request, BindingResult result);
    void deleteResume(Integer resumeId);
}
