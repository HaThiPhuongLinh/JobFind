package com.jobfind.models;

import com.jobfind.models.enums.ApplicationStatus;
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
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    private ApplicationStatus applicationStatus;

    private LocalDateTime appliedAt;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "job_seeker_profile_id")
    private JobSeekerProfile jobSeekerProfile;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
