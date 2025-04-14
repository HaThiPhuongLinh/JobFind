package com.jobfind.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonalInfoRequest {
    private Integer userId;
    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp = "^\\+?[0-9]{10,11}$", message = "Phone must be a valid phone number with 10 to 11 digits")
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String title;
    private String address;
    private String companyName;
    private String logoPath;
    private List<Integer> industryIds;
    private String website;
    private String description;
}