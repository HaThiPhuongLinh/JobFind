package com.jobfind.repositories;

import com.jobfind.models.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyIndustryRepository extends JpaRepository<Industry, Integer> {
}
