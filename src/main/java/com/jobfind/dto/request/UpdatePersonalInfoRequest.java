package com.jobfind.dto.request;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonalInfoRequest {
    private Integer userId;
    private String email;
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