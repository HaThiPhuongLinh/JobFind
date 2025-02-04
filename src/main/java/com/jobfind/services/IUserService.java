package com.jobfind.services;

import com.jobfind.dto.request.ResetPasswordRequest;
import com.jobfind.dto.request.UpdatePersonalInfoRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public interface IUserService {
    void updateProfileInfo(UpdatePersonalInfoRequest request, BindingResult bindingResult);
    void updatePassword(ResetPasswordRequest request, BindingResult bindingResult);
}
