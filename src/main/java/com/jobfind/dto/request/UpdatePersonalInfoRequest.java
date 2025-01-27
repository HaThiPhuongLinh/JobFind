package com.jobfind.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonalInfoRequest {
    private Integer userId;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email must be a valid email address")
    private String email;
    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp = "^\\+?[0-9]{10,11}$", message = "Phone must be a valid phone number with 10 to 11 digits")
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String resumePath;
    private String companyName;
    private String logoPath;
    private String industry;
    private String website;
    private String description;
}