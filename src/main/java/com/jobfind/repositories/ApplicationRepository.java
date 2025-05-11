package com.jobfind.repositories;

import com.jobfind.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findByJobJobIdAndJobSeekerProfileProfileId(Integer jobId, Integer profileId);
    List<Application> findByJobSeekerProfileProfileId(Integer profileId);
    List<Application> findByJobJobId(Integer jobId);
    List<Application> findTop5ByOrderByAppliedAtDesc();
    long countByAppliedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT j.location, COUNT(a) FROM Application a JOIN a.job j WHERE a.appliedAt BETWEEN :start AND :end GROUP BY j.location")
    List<Object[]> countByLocationAndCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}