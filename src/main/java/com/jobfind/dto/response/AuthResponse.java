package com.jobfind.dto.response;

import com.jobfind.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Integer id;
    private Integer userId;

    private String email;

    private String phone;
    private String avatar;

    private String token;

    private Role role;
}
