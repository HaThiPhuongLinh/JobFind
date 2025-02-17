package com.jobfind.repositories;

import com.jobfind.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {
    boolean existsByFileName(String fileName);
}