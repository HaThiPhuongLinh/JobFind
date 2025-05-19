package com.jobfind.models;

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
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resumeId;

    private String resumeName;
    private String resumePath;
    private LocalDateTime uploadedAt;
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private JobSeekerProfile jobSeekerProfile;
}
