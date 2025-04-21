package com.jobfind.services.impl;

import com.jobfind.config.AwsS3Service;
import com.jobfind.dto.request.ResetPasswordRequest;
import com.jobfind.dto.request.UpdatePersonalInfoRequest;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Company;
import com.jobfind.models.Industry;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.User;
import com.jobfind.models.enums.Role;
import com.jobfind.repositories.IndustryRepository;
import com.jobfind.repositories.CompanyRepository;
import com.jobfind.repositories.JobSeekerProfileRepository;
import com.jobfind.repositories.UserRepository;
import com.jobfind.services.IUserService;
import com.jobfind.utils.ValidateField;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final String CHANGE_TYPE_UPDATE = "UPDATE";
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final IndustryRepository industryRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidateField validateField;
    private final AwsS3Service awsS3Service;

    @Override
    public void updateProfileInfo(UpdatePersonalInfoRequest request, BindingResult bindingResult) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException("User not found"));

        Map<String, String> errors = validateField.getErrors(bindingResult);
        if (!errors.isEmpty()) {
            throw new BadRequestException("Validation errors", errors);
        }

        user.setPhone(request.getPhoneNumber());

        String logoPath = uploadFile(request.getLogoPath(), "logo");
        String avatarPath = uploadFile(request.getAvatar(), "avatar");

        if (Role.COMPANY.equals(user.getRole())) {
            Company company = companyRepository.findByUser_UserId(user.getUserId())
                    .orElseThrow(() -> new BadRequestException("Company not found with this user id"));

            validateField.getCompanyFieldErrors(errors, request.getCompanyName());

            company.setCompanyName(request.getCompanyName());
            company.setWebsite(request.getWebsite());
            company.setDescription(request.getDescription());

            if (logoPath != null) {
                company.setLogoPath(logoPath);
            }

            if (request.getIndustryIds() != null && !request.getIndustryIds().isEmpty()) {
                List<Industry> industries = industryRepository.findAllById(request.getIndustryIds());
                company.setIndustry(industries);
            } else {
                company.setIndustry(new ArrayList<>());
            }

            companyRepository.save(company);

        } else if (Role.JOBSEEKER.equals(user.getRole())) {
            JobSeekerProfile profile = jobSeekerProfileRepository.findByUser_UserId(user.getUserId())
                    .orElseThrow(() -> new BadRequestException("JobSeekerProfile not found with this user id"));

            validateField.getJobSeekerFieldErrors(errors, request.getFirstName(), request.getLastName(), request.getAddress());

            profile.setFirstName(request.getFirstName());
            profile.setLastName(request.getLastName());
            profile.setTitle(request.getTitle());
            profile.setAddress(request.getAddress());

            if (avatarPath != null) {
                profile.setAvatar(avatarPath);
            }

            jobSeekerProfileRepository.save(profile);
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        userRepository.save(user);
    }

    private String uploadFile(MultipartFile file, String fileType) {
        if (file != null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || !originalFileName.contains(".")) {
                throw new BadRequestException("Invalid " + fileType + " file name.");
            }

            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String baseName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
            String s3Key = baseName + "_" + System.currentTimeMillis() + extension;

            try {
                return awsS3Service.uploadFileToS3(file.getInputStream(), s3Key, file.getContentType());
            } catch (IOException e) {
                throw new BadRequestException("Failed to upload " + fileType + ".");
            }
        }
        return null;
    }

    @Override
    public void updatePassword(ResetPasswordRequest changePasswordRequest, BindingResult bindingResult) {
        User user = userRepository.findById(changePasswordRequest.getUserId()).orElseThrow(() -> new BadRequestException("User not found"));
        Map<String, String> errors = validateField.getErrors(bindingResult);

        if (CHANGE_TYPE_UPDATE.equalsIgnoreCase(changePasswordRequest.getChangeType())) {
            if (StringUtils.isEmpty(changePasswordRequest.getOldPassword())) {
                errors.put("oldPassword", "Old password cannot be empty");
            }

            if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPasswordHash())) {
                throw new BadRequestException("Old password is incorrect");
            }

            if (changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())) {
                throw new BadRequestException("New password cannot be the same as the old password");
            }
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        String encryptedPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        user.setPasswordHash(encryptedPassword);

        userRepository.save(user);
    }
}
