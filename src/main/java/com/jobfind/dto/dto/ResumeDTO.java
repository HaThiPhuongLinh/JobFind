package com.jobfind.dto.dto;

import lombok.*;

import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDTO {
    private Integer resumeId;
    private String resumeName;
    private String resumePath;
    private LocalDateTime uploadedAt;
}