package com.jobfind.services;

import com.jobfind.dto.dto.UserDTO;
import com.jobfind.dto.request.ResetPasswordRequest;
import com.jobfind.dto.request.UpdatePersonalInfoRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public interface IUserService {
    List<UserDTO> getAllUsers();
    void updateProfileInfo(UpdatePersonalInfoRequest request, BindingResult bindingResult);
    void updatePassword(ResetPasswordRequest request, BindingResult bindingResult);
}
