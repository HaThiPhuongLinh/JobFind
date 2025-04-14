package com.jobfind.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Industry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "industry_id")
    private Integer industryId;

    private String name;

    @OneToMany(mappedBy = "industry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobCategory> jobCategories = new ArrayList<>();
}
