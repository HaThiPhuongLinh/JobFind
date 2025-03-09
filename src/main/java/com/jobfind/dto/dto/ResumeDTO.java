package com.jobfind.dto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@Data
public class ResumeDTO {
    private Integer resumeId;
    private String resumeName;
    private String resumePath;
    private LocalDateTime uploadedAt;
}