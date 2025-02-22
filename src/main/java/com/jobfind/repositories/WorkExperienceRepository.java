package com.jobfind.repositories;

import com.jobfind.models.Company;
import com.jobfind.models.JobPosition;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Integer> {
    boolean existsByJobSeekerProfileAndCompanyAndJobPosition(JobSeekerProfile jobSeekerProfile, Company company, JobPosition jobPosition);
}