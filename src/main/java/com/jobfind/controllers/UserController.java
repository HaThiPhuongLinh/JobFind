package com.jobfind.controllers;

import com.jobfind.dto.request.ResetPasswordRequest;
import com.jobfind.dto.request.UpdatePersonalInfoRequest;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userServiceImpl;

    @PostMapping("/update-profile")
    public ResponseEntity<SuccessResponse> updateProfile(@RequestBody UpdatePersonalInfoRequest updatePersonalInfoRequest) {
        userServiceImpl.updatePersonalInfo(updatePersonalInfoRequest);
        return ResponseEntity.ok(new SuccessResponse("User profile updated successfully"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<SuccessResponse> changePassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        userServiceImpl.updatePassword(resetPasswordRequest);
        return ResponseEntity.ok(new SuccessResponse("Password updated successfully"));
    }
}
