package com.jobfind.repositories;

import com.jobfind.models.Company;
import com.jobfind.models.JobPosition;
import com.jobfind.models.User;
import com.jobfind.models.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Integer> {
    List<WorkExperience> findByUser_UserId(Integer userId);
    boolean existsByUserAndCompanyAndJobPosition(User user, Company company, JobPosition jobPosition);
}