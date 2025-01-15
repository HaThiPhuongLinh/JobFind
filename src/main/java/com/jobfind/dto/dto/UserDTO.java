package com.jobfind.dto.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private Integer userId;
    private String email;
    private String phone;
    private String role;
}
