package com.jobfind.services.impl;

import com.jobfind.config.JwtService;
import com.jobfind.dto.request.AuthRequest;
import com.jobfind.dto.request.RegistrationRequest;
import com.jobfind.dto.response.AuthResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Company;
import com.jobfind.models.Industry;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.User;
import com.jobfind.models.enums.Role;
import com.jobfind.repositories.CompanyRepository;
import com.jobfind.repositories.JobSeekerProfileRepository;
import com.jobfind.repositories.UserRepository;
import com.jobfind.services.IAuthService;
import com.jobfind.utils.ValidateField;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final UserRepository userRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ValidateField validateField;
    @Override
    public void register(RegistrationRequest registrationRequest, BindingResult result) {
        Map<String,String> errors = validateField.getErrors(result);
        if (registrationRequest.getRole() == Role.JOBSEEKER) {
            validateField.getJobSeekerFieldErrors(errors, registrationRequest.getFirstName(), registrationRequest.getLastName(), registrationRequest.getAddress());
        } else if (registrationRequest.getRole() == Role.COMPANY) {
            validateField.getCompanyFieldErrors(errors, registrationRequest.getCompanyName(), registrationRequest.getLogoPath());
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
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

        if (registrationRequest.getRole() == Role.JOBSEEKER) {
            jobSeekerProfileRepository.save(JobSeekerProfile.builder()
                    .firstName(registrationRequest.getFirstName())
                    .lastName(registrationRequest.getLastName())
                    .user(user)
                    .build());
        } else if (registrationRequest.getRole() == Role.COMPANY) {
            companyRepository.save(Company.builder()
                    .companyName(registrationRequest.getCompanyName())
                    .industry(registrationRequest.getIndustryIds()
                            .stream()
                            .map(industryId -> Industry.builder().industryId(industryId).build())
                            .collect(Collectors.toList()))
                    .logoPath(registrationRequest.getLogoPath())
                    .website(registrationRequest.getWebsite())
                    .description(registrationRequest.getDescription())
                    .isVerified(isVerified)
                    .user(user)
                    .build());
        }
    }

    @Override
    public AuthResponse login(AuthRequest authRequest, BindingResult result) {
        validateField.getErrors(result);

        if(!userRepository.existsByEmail(authRequest.getEmail()))
            throw new BadRequestException("Email not exists");

        User user = userRepository.findByEmail(authRequest.getEmail()).get();

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash()))
            throw new BadRequestException("Password is incorrect");

        String token = jwtService.generateToken(user.getEmail());

        Integer returnId = null;

        switch (user.getRole()) {
            case JOBSEEKER:
                JobSeekerProfile profile = jobSeekerProfileRepository.findByUser_UserId(user.getUserId())
                        .orElseThrow(() -> new BadRequestException("JobSeekerProfile not found"));
                returnId = profile.getProfileId();
                break;
            case COMPANY:
                Company company = companyRepository.findByUser_UserId(user.getUserId())
                        .orElseThrow(() -> new BadRequestException("Company not found"));
                returnId = company.getCompanyId();
                break;
        }

        return AuthResponse.builder()
                .token(token)
                .id(user.getUserId())
                .userId(returnId)
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }
}
