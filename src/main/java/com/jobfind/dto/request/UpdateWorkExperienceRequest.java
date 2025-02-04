package com.jobfind.dto.request;

import com.jobfind.models.enums.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWorkExperienceRequest {
    @NotNull(message = "workExperienceId cannot be empty")
    private Integer workExperienceId;
    @NotNull(message = "jobPositionId cannot be empty")
    private Integer jobPositionId;
    @NotNull(message = "Id cannot be empty")
    private Integer companyId;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    private JobType jobType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Start Date must not be in the future")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "End Date must not be in the future")
    private LocalDate endDate;
    private List<Integer> skills;
}
