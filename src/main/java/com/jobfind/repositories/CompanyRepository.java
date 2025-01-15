package com.jobfind.repositories;

import com.jobfind.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByUser_UserId(Integer userId);
}