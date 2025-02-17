package com.jobfind.dto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CompanyDTO {
    private Integer companyId;
    private String companyName;
    private String logoPath;
    private String industry;
    private String website;
    private String description;
    private String email;
    private String phone;
}
