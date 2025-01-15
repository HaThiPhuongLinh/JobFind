package com.jobfind.services.impl;

import com.jobfind.config.JwtService;
import com.jobfind.dto.request.AuthRequest;
import com.jobfind.dto.request.RegistrationRequest;
import com.jobfind.dto.response.AuthResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Company;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.User;
import com.jobfind.models.enums.Role;
import com.jobfind.repositories.CompanyRepository;
import com.jobfind.repositories.JobSeekerProfileRepository;
import com.jobfind.repositories.UserRepository;
import com.jobfind.services.IAuthService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public void register(RegistrationRequest registrationRequest) {
        Map<String, String> infoMessage = new HashMap<>();

        if (StringUtils.isEmpty(registrationRequest.getPhone())) {
            infoMessage.put("phoneNumber", "Phone mumber must be required.");
        }

        if (StringUtils.isEmpty(registrationRequest.getEmail())) {
            infoMessage.put("email", "Email must be required.");
        }

        if (StringUtils.isEmpty(registrationRequest.getPassword())) {
            infoMessage.put("password", "Password must be required.");
        }

        if (registrationRequest.getRole() == null) {
            infoMessage.put("role", "Role must be specified (JobSeeker, Company).");
        }

        if (registrationRequest.getRole() == Role.JobSeeker) {
            if (StringUtils.isEmpty(registrationRequest.getFirstName())) {
                infoMessage.put("firstName", "First name must be required.");
            }
            if (StringUtils.isEmpty(registrationRequest.getLastName())) {
                infoMessage.put("lastName", "Last name must be required.");
            }
            if (StringUtils.isEmpty(registrationRequest.getResumePath())) {
                infoMessage.put("resumePath", "Resume path must be required.");
            }
        } else if (registrationRequest.getRole() == Role.Company) {
            if (StringUtils.isEmpty(registrationRequest.getCompanyName())) {
                infoMessage.put("companyName", "Company name must be required.");
            }
            if (StringUtils.isEmpty(registrationRequest.getIndustry())) {
                infoMessage.put("industry", "Industry must be specified.");
            }
            if (StringUtils.isEmpty(registrationRequest.getLogoPath())) {
                infoMessage.put("logoPath", "Logo Path must be specified.");
            }
        }

        if (!infoMessage.isEmpty()){
            throw new BadRequestException("Please complete all required fields to proceed.", infoMessage);
        }

        if(userRepository.existsByEmail(registrationRequest.getEmail()))
            throw new BadRequestException("Email already in use");

        User user = User.builder()
                .email(registrationRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registrationRequest.getPassword()))
                .phone(registrationRequest.getPhone())
                .role(registrationRequest.getRole())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .build();

        userRepository.save(user);

        Boolean isVerified = registrationRequest.getIsVerified() != null && registrationRequest.getIsVerified();

        if (registrationRequest.getRole() == Role.JobSeeker) {
            jobSeekerProfileRepository.save(JobSeekerProfile.builder()
                    .firstName(registrationRequest.getFirstName())
                    .lastName(registrationRequest.getLastName())
                    .resumePath(registrationRequest.getResumePath())
                    .user(user)
                    .build());
        } else if (registrationRequest.getRole() == Role.Company) {
            companyRepository.save(Company.builder()
                    .companyName(registrationRequest.getCompanyName())
                    .industry(registrationRequest.getIndustry())
                    .logoPath(registrationRequest.getLogoPath())
                    .website(registrationRequest.getWebsite())
                    .description(registrationRequest.getDescription())
                    .isVerified(isVerified)
                    .user(user)
                    .build());
        }
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        if (StringUtils.isEmpty(authRequest.getEmail())) {
            throw new BadRequestException("Email must be required");
        }

        if (StringUtils.isEmpty(authRequest.getPassword())) {
            throw new BadRequestException("Password must be required");
        }

        if(!userRepository.existsByEmail(authRequest.getEmail()))
            throw new BadRequestException("Email not exists");

        User user = userRepository.findByEmail(authRequest.getEmail()).get();

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash()))
            throw new BadRequestException("Password is incorrect");

        String token = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .userId(user.getUserId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }
}
