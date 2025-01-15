package com.jobfind.services;

import com.jobfind.dto.request.ResetPasswordRequest;
import com.jobfind.dto.request.UpdatePersonalInfoRequest;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    void updatePersonalInfo(UpdatePersonalInfoRequest request);
    void updatePassword(ResetPasswordRequest request);
}
