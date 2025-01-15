package com.jobfind.services.impl;

import com.jobfind.dto.request.ResetPasswordRequest;
import com.jobfind.dto.request.UpdatePersonalInfoRequest;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Company;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.User;
import com.jobfind.models.enums.Role;
import com.jobfind.repositories.CompanyRepository;
import com.jobfind.repositories.JobSeekerProfileRepository;
import com.jobfind.repositories.UserRepository;
import com.jobfind.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final String UPDATE_TYPE_RESET = "reset";

    @Override
    public void updatePersonalInfo(UpdatePersonalInfoRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new BadRequestException("User not found"));

        user.setEmail(request.getEmail());
        user.setPhone(request.getPhoneNumber());

        if (Role.Company.equals(user.getRole())) {
            Company company = companyRepository.findByUser_UserId(user.getUserId()).orElseThrow(() -> new BadRequestException("Company not found with this user id"));
            company.setCompanyName(request.getCompanyName());
            company.setLogoPath(request.getLogoPath());
            company.setIndustry(request.getIndustry());
            company.setWebsite(request.getWebsite());
            company.setDescription(request.getDescription());
            companyRepository.save(company);
        } else if (Role.JobSeeker.equals(user.getRole())) {
            JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findByUser_UserId(user.getUserId()).orElseThrow(() -> new BadRequestException("JobSeekerProfile not found with this user id"));
            jobSeekerProfile.setFirstName(request.getFirstName());
            jobSeekerProfile.setLastName(request.getLastName());
            jobSeekerProfile.setResumePath(request.getResumePath());
            jobSeekerProfileRepository.save(jobSeekerProfile);
        }

        userRepository.save(user);
    }

    @Override
    public void updatePassword(ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadRequestException("User not found"));

        if (UPDATE_TYPE_RESET.equalsIgnoreCase(request.getUpdateType())) {
            if (StringUtils.isEmpty(request.getNewPassword())) {
                throw new BadRequestException("New password cannot be empty");
            }
        } else {
            if (StringUtils.isEmpty(request.getOldPassword())) {
                throw new BadRequestException("Old password is required");
            }

            if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash())) {
                throw new BadRequestException("Old password is incorrect");
            }

            if (StringUtils.isEmpty(request.getNewPassword())) {
                throw new BadRequestException("New password cannot be empty");
            }

            if (request.getOldPassword().equals(request.getNewPassword())) {
                throw new BadRequestException("New password cannot be the same as the old password");
            }
        }

        String encryptedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPasswordHash(encryptedPassword);

        userRepository.save(user);
    }
}
