package com.jobfind.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class ResumeRequest {
    @NotBlank(message = "Resume name cannot be empty")
    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;
}
