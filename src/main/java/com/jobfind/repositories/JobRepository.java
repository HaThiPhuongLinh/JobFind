package com.jobfind.repositories;

import com.jobfind.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {
    @Query("SELECT j FROM Job j " +
            "LEFT JOIN j.company c " +
            "LEFT JOIN j.categories cat " +
            "WHERE (:jobCategoryId IS NULL OR :jobCategoryId = 0 OR cat.jobCategoryId = :jobCategoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(c.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.requirements) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:location IS NULL OR :location = '' OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) " +
            "ORDER BY " +
            "CASE WHEN cat.jobCategoryId = :jobCategoryId THEN 1 " +
            "WHEN LOWER(c.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 2 " +
            "WHEN LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 3 " +
            "WHEN LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.requirements) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 4 ELSE 5 END")
    List<Job> searchJobs(@Param("keyword") String keyword,
                         @Param("location") String location,
                         @Param("jobCategoryId") Integer jobCategoryId);

    List<Job> findByCompanyCompanyId(Integer companyId);

    @Query("SELECT j FROM Job j " +
            "JOIN j.categories c " +
            "WHERE c.jobCategoryId = :categoryId AND j.isActive = true AND j.isDeleted = false")
    List<Job> findByCategoryId(Integer categoryId);
}