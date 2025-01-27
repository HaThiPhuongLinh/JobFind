package com.jobfind.services;

import com.jobfind.dto.request.AuthRequest;
import com.jobfind.dto.request.RegistrationRequest;
import com.jobfind.dto.response.AuthResponse;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public interface IAuthService {
    void register(RegistrationRequest registrationRequest, BindingResult result);
    AuthResponse login(AuthRequest authRequest, BindingResult result);
}
