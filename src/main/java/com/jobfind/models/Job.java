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
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @Column(columnDefinition = "LONGTEXT")
    private String requirements;
    @Column(columnDefinition = "LONGTEXT")
    private String benefits;

    private double salaryMin;

    private double salaryMax;
    private String yearsOfExperience;
    private String educationLevel;

    private JobType jobType;

    private String location;

    private LocalDateTime postedAt;

    private LocalDate deadline;
    private String note;
    private Boolean isPending;
    private Boolean isPriority;
    private Integer priorityLevel;

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

    @Column(columnDefinition = "boolean default false")
    private Boolean isApproved;
}