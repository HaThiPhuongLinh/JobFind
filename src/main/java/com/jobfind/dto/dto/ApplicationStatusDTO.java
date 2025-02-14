package com.jobfind.dto.dto;

import com.jobfind.models.enums.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationStatusDTO {
    private ApplicationStatus status;
    private LocalDateTime time;
}
