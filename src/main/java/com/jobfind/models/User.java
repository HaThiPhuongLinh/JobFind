package com.jobfind.models;

import com.jobfind.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String email;

    private String phone;

    private String passwordHash;

    private Role role;

    private LocalDateTime createdAt;

    private String otp;

    private LocalDateTime otpExpiry;

    private boolean isVerified;
    private boolean isVip;
    private Integer vipLevel;
    private LocalDateTime vipExpiryDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private JobSeekerProfile jobSeekerProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Company company;
}