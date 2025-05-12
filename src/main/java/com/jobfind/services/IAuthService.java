package com.jobfind.services;

import com.jobfind.dto.request.AuthRequest;
import com.jobfind.dto.request.RegistrationRequest;
import com.jobfind.dto.request.VerifyOtpRequest;
import com.jobfind.dto.response.AuthResponse;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.io.IOException;

@Service
public interface IAuthService {
    void register(RegistrationRequest registrationRequest, BindingResult result) throws IOException;
    AuthResponse login(AuthRequest authRequest, BindingResult result);
    void resendOtp(String email);
    void verifyOtp(VerifyOtpRequest request);
}
