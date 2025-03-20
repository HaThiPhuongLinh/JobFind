package com.jobfind.repositories;

import com.jobfind.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Company> findByUser_UserId(Integer userId);
    @Query("SELECT c FROM Company c JOIN c.industry i " +
            "WHERE (:industryId IS NULL OR i.industryId = :industryId) " +
            "AND (:companyName IS NULL OR c.companyName LIKE %:companyName%)")
    List<Company> findByIndustryOrCompanyName(@Param("industryId") Integer industryId,
                                              @Param("companyName") String companyName);
}