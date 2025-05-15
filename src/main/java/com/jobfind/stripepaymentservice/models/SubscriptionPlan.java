package com.jobfind.stripepaymentservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private Double price;
    private Long durationMonths;

    private Boolean isActive;
    private Integer monthlyJobPostLimit;
    private Integer jobPriorityLevel;
    private Boolean isFeaturedOnHomepage;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
