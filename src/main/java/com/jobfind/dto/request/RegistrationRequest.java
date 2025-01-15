package com.jobfind.dto.request;

import com.jobfind.models.enums.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String email;

    private String phone;

    private String password;

    private String firstName;

    private String lastName;

    private String resumePath;

    private Role role;
}
