package com.jobfind.dto.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
@Builder
@Data
public class RecentApplicationDTO {
    private Integer id;
    private String applicant;
    private String jobTitle;
    private String status;
    private String date;
}