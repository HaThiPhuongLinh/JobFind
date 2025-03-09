package com.jobfind.repositories;

import com.jobfind.models.CompanyReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Integer> {
    List<CompanyReview> findByCompanyCompanyId(Integer companyId);
    Optional<CompanyReview> findByCompanyCompanyIdAndJobSeekerProfileProfileId(Integer companyId, Integer profileId);
}