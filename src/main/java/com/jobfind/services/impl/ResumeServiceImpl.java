package com.jobfind.services.impl;

import com.jobfind.config.AwsS3Service;
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

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements IResumeService {
    private final ResumeRepository resumeRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final ValidateField validateField;
    private final AwsS3Service awsS3Service;
    @Override
    public void createResume(Integer profileId, ResumeRequest request, BindingResult result) throws IOException {
        Map<String, String> errors = validateField.getErrors(result);

        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        JobSeekerProfile profile = jobSeekerProfileRepository.findById(profileId)
                .orElseThrow(() -> new BadRequestException("Profile not found"));

        boolean isResumeExist = resumeRepository.existsByResumeNameAndJobSeekerProfileProfileId(request.getResumeName(), profile.getProfileId());

        if (isResumeExist) {
            throw new BadRequestException("Resume name has been used");
        }

        String resumeFileName = request.getResume().getOriginalFilename();
        InputStream inputStream = request.getResume().getInputStream();
        String contentType = request.getResume().getContentType();
        String extension = resumeFileName.substring(resumeFileName.lastIndexOf("."));
        String baseName = resumeFileName.substring(0, resumeFileName.lastIndexOf("."));
        String s3Key = baseName + "_" + System.currentTimeMillis() + extension;
        String s3Url = awsS3Service.uploadFileToS3(inputStream, s3Key, contentType);

        Resume resume = Resume.builder()
                .jobSeekerProfile(profile)
                .resumeName(request.getResumeName())
                .resumePath(s3Url)
                .uploadedAt(LocalDateTime.now())
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
