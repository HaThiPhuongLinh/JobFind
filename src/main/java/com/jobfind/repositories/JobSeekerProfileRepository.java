package com.jobfind.repositories;

import com.jobfind.models.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Integer> {
    Optional<JobSeekerProfile> findByUser_UserId(Integer userId);
    @Query("SELECT DISTINCT j FROM JobSeekerProfile j " +
            "JOIN j.workExperiences we " +
            "LEFT JOIN we.skills wes " +
            "LEFT JOIN we.company c " +
            "LEFT JOIN we.jobPosition jp " +
            "LEFT JOIN j.skills js " +
            "JOIN we.categories jc " +
            "WHERE (:categoryIds IS NULL OR jc.jobCategoryId IN :categoryIds) " +
            "AND (" +
            "       (:keyword IS NOT NULL AND EXISTS (" +
            "           SELECT 1 FROM WorkExperience we2 " +
            "           JOIN we2.skills wes2 " +
            "           WHERE we2.jobSeekerProfile = j " +
            "           AND LOWER(wes2.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "       )) " +
            "       OR (" +
            "           :keyword IS NOT NULL AND NOT EXISTS (" +
            "               SELECT 1 FROM WorkExperience we3 " +
            "               JOIN we3.skills wes3 " +
            "               WHERE we3.jobSeekerProfile = j " +
            "               AND LOWER(wes3.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "           ) " +
            "           AND EXISTS (" +
            "               SELECT 1 FROM JobSeekerProfile j2 " +
            "               JOIN j2.skills js2 " +
            "               WHERE j2 = j AND LOWER(js2.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "           )" +
            "       )" +
            ") " +
            "AND (:location IS NULL OR LOWER(j.address) LIKE LOWER(CONCAT('%', :location, '%')))")
    List<JobSeekerProfile> searchJobSeekers(
            @Param("keyword") String keyword,
            @Param("categoryIds") List<Integer> categoryIds,
            @Param("location") String location);

    @Query("""
    SELECT DISTINCT j FROM JobSeekerProfile j
    JOIN j.workExperiences we
    JOIN we.categories jc
    JOIN Industry i ON jc.industry = i
    JOIN Company c ON i MEMBER OF c.industry
    WHERE c.companyId = :companyId
    """)
    List<JobSeekerProfile> findJobSeekersByCompanyIndustry(@Param("companyId") Integer companyId);
}