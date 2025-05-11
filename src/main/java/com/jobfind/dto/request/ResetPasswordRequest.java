package com.jobfind.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.annotations.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    private Integer userId;
    private String oldPassword;
    @NotBlank(message = "New password cannot be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,20}$",
            message = "New password must be between 6 and 20 characters, contain at least one lowercase letter, one uppercase letter, and one digit")
    private String newPassword;
    @Pattern(regexp = "RESET|UPDATE", message = "Change type must be one of the following values: RESET, UPDATE")
    private String changeType;
}