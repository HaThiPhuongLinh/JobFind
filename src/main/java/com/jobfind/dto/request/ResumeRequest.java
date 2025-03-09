package com.jobfind.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@Builder
public class ResumeRequest {
    @NotBlank(message = "Resume name cannot be empty")
    private String resumeName;
    private MultipartFile resume;
}
