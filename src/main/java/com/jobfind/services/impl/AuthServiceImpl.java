package com.jobfind.services.impl;

import com.jobfind.config.AwsS3Service;
import com.jobfind.config.JwtService;
import com.jobfind.constants.JobFindConstant;
import com.jobfind.dto.request.AuthRequest;
import com.jobfind.dto.request.RegistrationRequest;
import com.jobfind.dto.request.VerifyOtpRequest;
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
import com.jobfind.services.EmailService;
import com.jobfind.services.IAuthService;
import com.jobfind.utils.ValidateField;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
    private final AwsS3Service awsS3Service;
    private final EmailService emailService;

    private final Map<String, RegistrationRequest> pendingRegistrations = new HashMap<>();
    private final Map<String, String> pendingOtps = new HashMap<>();
    private final Map<String, LocalDateTime> pendingOtpExpiries = new HashMap<>();

    @Override
    public void register(RegistrationRequest registrationRequest, BindingResult result) throws IOException {
        Map<String, String> errors = validateField.getErrors(result);
        if (registrationRequest.getRole() == Role.JOBSEEKER) {
            validateField.getJobSeekerFieldErrors(errors, registrationRequest.getFirstName(), registrationRequest.getLastName(), registrationRequest.getAddress());
        } else if (registrationRequest.getRole() == Role.COMPANY) {
            validateField.getCompanyFieldErrors(errors, registrationRequest.getCompanyName());
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new BadRequestException("Email already in use");
        }

        if (registrationRequest.getRole() == Role.JOBSEEKER) {
            User user = User.builder()
                    .email(registrationRequest.getEmail())
                    .passwordHash(passwordEncoder.encode(registrationRequest.getPassword()))
                    .phone(registrationRequest.getPhone())
                    .role(registrationRequest.getRole())
                    .createdAt(LocalDateTime.now())
                    .isVerified(true)
                    .build();

            userRepository.save(user);

            jobSeekerProfileRepository.save(JobSeekerProfile.builder()
                    .firstName(registrationRequest.getFirstName())
                    .lastName(registrationRequest.getLastName())
                    .avatar(JobFindConstant.AVATAR_URL_DEFAULT)
                    .user(user)
                    .build());
        } else if (registrationRequest.getRole() == Role.COMPANY) {
            String otp = String.format("%06d", new Random().nextInt(999999));
            LocalDateTime otpExpiry = LocalDateTime.now().plusMinutes(10);

            pendingRegistrations.put(registrationRequest.getEmail(), registrationRequest);
            pendingOtps.put(registrationRequest.getEmail(), otp);
            pendingOtpExpiries.put(registrationRequest.getEmail(), otpExpiry);

            try {
                emailService.sendOtpEmail(registrationRequest.getEmail(), otp);
            } catch (Exception e) {
                pendingRegistrations.remove(registrationRequest.getEmail());
                pendingOtps.remove(registrationRequest.getEmail());
                pendingOtpExpiries.remove(registrationRequest.getEmail());
                throw new BadRequestException("Failed to send OTP email: " + e.getMessage());
            }
        }
    }

    @Override
    public AuthResponse login(AuthRequest authRequest, BindingResult result) {
        validateField.getErrors(result);

        if (!userRepository.existsByEmail(authRequest.getEmail())) {
            throw new BadRequestException("Email not exists");
        }

        User user = userRepository.findByEmail(authRequest.getEmail()).get();

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPasswordHash())) {
            throw new BadRequestException("Password is incorrect");
        }

        String token = jwtService.generateToken(user.getEmail());

        Integer returnId = null;
        String avatar = null;

        switch (user.getRole()) {
            case JOBSEEKER:
                JobSeekerProfile profile = jobSeekerProfileRepository.findByUser_UserId(user.getUserId())
                        .orElseThrow(() -> new BadRequestException("JobSeekerProfile not found"));
                returnId = profile.getProfileId();
                avatar = profile.getAvatar();
                break;
            case COMPANY:
                Company company = companyRepository.findByUser_UserId(user.getUserId())
                        .orElseThrow(() -> new BadRequestException("Company not found"));
                returnId = company.getCompanyId();
                avatar = company.getLogoPath();
                break;
        }

        return AuthResponse.builder()
                .token(token)
                .id(user.getUserId())
                .userId(returnId)
                .email(user.getEmail())
                .avatar(avatar)
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }

    @Override
    public void verifyOtp(VerifyOtpRequest request) {
        if (!pendingRegistrations.containsKey(request.getEmail())) {
            throw new BadRequestException("No registration found for this email");
        }

        String storedOtp = pendingOtps.get(request.getEmail());
        LocalDateTime otpExpiry = pendingOtpExpiries.get(request.getEmail());

        if (storedOtp == null || otpExpiry == null) {
            throw new BadRequestException("No OTP found for this email");
        }

        if (LocalDateTime.now().isAfter(otpExpiry)) {
            pendingRegistrations.remove(request.getEmail());
            pendingOtps.remove(request.getEmail());
            pendingOtpExpiries.remove(request.getEmail());
            throw new BadRequestException("OTP has expired");
        }

        if (!storedOtp.equals(request.getOtp())) {
            throw new BadRequestException("Invalid OTP");
        }

        RegistrationRequest registrationRequest = pendingRegistrations.get(request.getEmail());

        User user = User.builder()
                .email(registrationRequest.getEmail())
                .passwordHash(passwordEncoder.encode(registrationRequest.getPassword()))
                .phone(registrationRequest.getPhone())
                .role(registrationRequest.getRole())
                .createdAt(LocalDateTime.now())
                .isVerified(true)
                .build();

        userRepository.save(user);

        String logoPath = null;

        if (registrationRequest.getLogoPath() != null && !registrationRequest.getLogoPath().isEmpty()) {
            String originalFileName = registrationRequest.getLogoPath().getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String baseName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
            String s3Key = baseName + "_" + System.currentTimeMillis() + extension;
            try {
                logoPath = awsS3Service.uploadFileToS3(registrationRequest.getLogoPath().getInputStream(), s3Key, registrationRequest.getLogoPath().getContentType());
            } catch (IOException e) {
                userRepository.delete(user);
                throw new BadRequestException("Failed to upload avatar.");
            }
        }

        companyRepository.save(Company.builder()
                .companyName(registrationRequest.getCompanyName())
                .industry(registrationRequest.getIndustryIds()
                        .stream()
                        .map(industryId -> Industry.builder().industryId(industryId).build())
                        .collect(Collectors.toList()))
                .logoPath(logoPath)
                .website(registrationRequest.getWebsite())
                .description(registrationRequest.getDescription())
                .isVerified(true)
                .user(user)
                .build());

        pendingRegistrations.remove(request.getEmail());
        pendingOtps.remove(request.getEmail());
        pendingOtpExpiries.remove(request.getEmail());
    }

    @Override
    public void resendOtp(String email) {
        if (!pendingRegistrations.containsKey(email)) {
            throw new BadRequestException("No registration found for this email");
        }

        String otp = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime otpExpiry = LocalDateTime.now().plusMinutes(10);

        pendingOtps.put(email, otp);
        pendingOtpExpiries.put(email, otpExpiry);

        try {
            emailService.sendOtpEmail(email, otp);
        } catch (Exception e) {
            throw new BadRequestException("Failed to send OTP email: " + e.getMessage());
        }
    }
}