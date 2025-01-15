package com.jobfind.dto.request;

import com.jobfind.models.enums.Role;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String email;
    private String password;
    private String phone;

    private String firstName;
    private String lastName;
    private String resumePath;

    private String companyName;
    private String industry;
    private String logoPath;
    private String website;
    private String description;

    private Boolean isVerified;
    private Role role;
}
