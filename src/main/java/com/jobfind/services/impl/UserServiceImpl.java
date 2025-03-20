package com.jobfind.services.impl;

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

    @Override
    public void updateProfileInfo(UpdatePersonalInfoRequest request, BindingResult bindingResult) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new BadRequestException("User not found"));
        Map<String, String> errors = validateField.getErrors(bindingResult);

        user.setPhone(request.getPhoneNumber());

        if (Role.COMPANY.equals(user.getRole())) {
            Company company = companyRepository.findByUser_UserId(user.getUserId()).orElseThrow(() -> new BadRequestException("Company not found with this user id"));
            validateField.getCompanyFieldErrors(errors, request.getCompanyName(), request.getLogoPath());
            company.setCompanyName(request.getCompanyName());
            company.setLogoPath(request.getLogoPath());
            if (request.getIndustryIds() != null && !request.getIndustryIds().isEmpty()) {
                List<Industry> industries = industryRepository.findAllById(request.getIndustryIds());
                company.setIndustry(industries);
            } else {
                company.setIndustry(new ArrayList<>());
            }
            company.setWebsite(request.getWebsite());
            company.setDescription(request.getDescription());
            companyRepository.save(company);
        } else if (Role.JOBSEEKER.equals(user.getRole())) {
            JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findByUser_UserId(user.getUserId()).orElseThrow(() -> new BadRequestException("JobSeekerProfile not found with this user id"));
            validateField.getJobSeekerFieldErrors(errors, request.getFirstName(), request.getLastName(), request.getAddress());
            jobSeekerProfile.setFirstName(request.getFirstName());
            jobSeekerProfile.setLastName(request.getLastName());
            jobSeekerProfile.setAddress(request.getAddress());
            jobSeekerProfileRepository.save(jobSeekerProfile);
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        userRepository.save(user);
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
