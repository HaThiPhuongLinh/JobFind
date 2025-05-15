package com.jobfind.repositories;

import com.jobfind.models.CardCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardCompanyRepository extends JpaRepository<CardCompany, Integer> {
    CardCompany findByUserUserId(Integer userId);
}