package com.jobfind.models;

import com.jobfind.models.enums.JobType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workExperienceId;

    private JobType jobType;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private JobSeekerProfile jobSeekerProfile;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "jobPosition_id")
    private JobPosition jobPosition;

    @ManyToMany
    @JoinTable(
            name = "WorkExperience_Skill",
            joinColumns = @JoinColumn(name = "workExperience_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;

    @ManyToMany
    @JoinTable(
            name = "WorkExperience_JobCategory",
            joinColumns = @JoinColumn(name = "workExperience_id"),
            inverseJoinColumns = @JoinColumn(name = "job_category_id")
    )
    private List<JobCategory> categories;
}