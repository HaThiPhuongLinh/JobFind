package com.jobfind.controllers;

import com.jobfind.dto.request.AuthRequest;
import com.jobfind.dto.request.RegistrationRequest;
import com.jobfind.dto.response.AuthResponse;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAuthService authServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        authServiceImpl.register(registrationRequest);
        return ResponseEntity.ok(new SuccessResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authServiceImpl.login(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}
