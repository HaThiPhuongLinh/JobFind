package com.jobfind.repositories;

import com.jobfind.models.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPositionRepository extends JpaRepository<JobPosition, Integer> {
}