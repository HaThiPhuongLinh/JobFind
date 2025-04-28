package com.jobfind.dto.request;

import com.jobfind.models.enums.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class UpdateJobRequest {
    private Integer jobId;
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
    @NotBlank(message = "Years of experience is required")
    private String yearsOfExperience;
    @NotBlank(message = "Education level is required")
    private String educationLevel;
    private JobType jobType;
    @NotBlank(message = "Location is required")
    private String location;
    private LocalDate deadline;
    private Boolean isActive;
    private List<Integer> skillIds;
    private List<Integer> categoryIds;
}
