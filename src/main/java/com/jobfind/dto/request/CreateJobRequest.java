package com.jobfind.dto.request;

import com.jobfind.models.enums.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobRequest {
    @NotNull(message = "Company ID is required")
    private Integer companyId;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Requirements is required")
    private String requirements;
    @NotBlank(message = "Benefits is required")
    private String benefits;
    @NotNull(message = "Salary min is required")
    @PositiveOrZero(message = "Salary min must be zero or positive")
    private double salaryMin;
    @NotNull(message = "Salary max is required")
    @PositiveOrZero(message = "Salary min must be zero or positive")
    private double salaryMax;
    private JobType jobType;
    @NotBlank(message = "Location is required")
    private String location;
    private LocalDate deadline;
    private List<Integer> skillIds;
    private List<Integer> categoryIds;
}