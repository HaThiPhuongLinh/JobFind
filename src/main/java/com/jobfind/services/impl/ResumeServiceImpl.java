package com.jobfind.services.impl;

import com.jobfind.dto.request.ResumeRequest;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.Resume;
import com.jobfind.repositories.JobSeekerProfileRepository;
import com.jobfind.repositories.ResumeRepository;
import com.jobfind.services.IResumeService;
import com.jobfind.utils.ValidateField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements IResumeService {
    private final ResumeRepository resumeRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final ValidateField validateField;

    @Override
    public void createResume(Integer profileId, ResumeRequest request, BindingResult result) {
        Map<String, String> errors = validateField.getErrors(result);

        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        JobSeekerProfile profile = jobSeekerProfileRepository.findById(profileId)
                .orElseThrow(() -> new BadRequestException("Profile not found"));

        if(resumeRepository.existsByFileName(request.getFileName())){
            throw new BadRequestException("Resume name has been used");
        }

        Resume resume = Resume.builder()
                .jobSeekerProfile(profile)
                .fileName(request.getFileName())
                .filePath(request.getFilePath())
                .uploadedAt(request.getUploadedAt())
                .build();

        resumeRepository.save(resume);
    }

    @Override
    public void deleteResume(Integer resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new BadRequestException("Resume not found"));
        resumeRepository.delete(resume);
    }
}
