package com.jobfind.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SkillRequest {
    @NotNull(message = "ProfileId cannot be empty")
    private Integer profileId;

    @NotNull(message = "Skills cannot be empty")
    private List<Integer> skills;
}