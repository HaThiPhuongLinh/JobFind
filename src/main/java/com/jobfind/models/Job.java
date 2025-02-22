package com.jobfind.models;

import com.jobfind.models.enums.JobType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobId;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String title;

    private String description;

    private String requirements;

    private String benefits;

    private double salaryMin;

    private double salaryMax;

    private JobType jobType;

    private String location;

    private LocalDateTime postedAt;

    private LocalDate deadline;

    @Column(columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted;
    @ManyToMany
    @JoinTable(name = "Job_Skill", joinColumns = @JoinColumn(name = "job_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills;
    @ManyToMany
    @JoinTable(name = "Job_JobCategory", joinColumns = @JoinColumn(name = "job_id"), inverseJoinColumns = @JoinColumn(name = "job_category_id"))
    private List<JobCategory> categories;
}