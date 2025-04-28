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
            "WHERE (:jobCategoryIds IS NULL OR cat.jobCategoryId IN :jobCategoryIds) " +
            "AND (:locations IS NULL OR j.location IN :locations) " +
            "AND (:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(c.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.requirements) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY " +
            "CASE WHEN cat.jobCategoryId IN :jobCategoryIds THEN 1 " +
            "WHEN LOWER(c.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 2 " +
            "WHEN LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 3 " +
            "WHEN LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.requirements) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN 4 ELSE 5 END")
    List<Job> searchJobs(@Param("keyword") String keyword,
                         @Param("locations") List<String> locations,
                         @Param("jobCategoryIds") List<Integer> jobCategoryIds);

    List<Job> findByCompanyCompanyId(Integer companyId);

    @Query("SELECT j FROM Job j " +
            "JOIN j.categories c " +
            "WHERE c.jobCategoryId = :categoryId AND j.isActive = true AND j.isDeleted = false")
    List<Job> findByCategoryId(Integer categoryId);

    @Query("SELECT DISTINCT j FROM Job j " +
            "LEFT JOIN j.skills s " +
            "LEFT JOIN j.categories c " +
            "WHERE (s.skillId IN :skillIds OR c.jobCategoryId IN :categoryIds) " +
            "AND j.isActive = true AND j.isDeleted = false AND j.isApproved = true")
    List<Job> findProposedJobs(@Param("skillIds") List<Integer> skillIds,
                               @Param("categoryIds") List<Integer> categoryIds);
}