package com.jobfind.dto.request;

import com.jobfind.models.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,20}$",
            message = "Password must be between 6 and 20 characters, contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;
    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp = "^\\+?[0-9]{10,11}$", message = "Phone must be a valid phone number with 10 to 11 digits")
    private String phone;
    private String firstName;
    private String lastName;

    private String companyName;
    private List<Integer> industryIds;
    private MultipartFile logoPath;
    private String website;
    private String description;
    private String address;

    private Boolean isVerified;
    @NotNull(message = "Role cannot be null")
    private Role role;
}
