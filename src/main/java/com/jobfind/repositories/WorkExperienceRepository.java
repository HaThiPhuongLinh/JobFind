package com.jobfind.repositories;

import com.jobfind.models.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Integer> {
}