package com.jobfind.repositories;

import com.jobfind.models.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Integer> {
    boolean existsByName(String name);
}