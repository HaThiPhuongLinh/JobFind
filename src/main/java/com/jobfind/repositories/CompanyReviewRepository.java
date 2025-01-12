package com.jobfind.repositories;

import com.jobfind.models.CompanyReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Integer> {
}