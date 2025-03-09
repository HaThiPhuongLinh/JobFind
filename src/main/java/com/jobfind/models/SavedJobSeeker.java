package com.jobfind.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SavedJobSeeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer savedJobSeekerId;

    private LocalDate savedAt;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "job_seeker_profile_id")
    private JobSeekerProfile jobSeekerProfile;
}
