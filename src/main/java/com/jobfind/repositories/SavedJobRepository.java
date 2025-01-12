package com.jobfind.repositories;

import com.jobfind.models.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedJobRepository extends JpaRepository<SavedJob, Integer> {
}