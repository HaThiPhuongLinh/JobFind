package com.jobfind.repositories;

import com.jobfind.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findByJobJobIdAndJobSeekerProfileProfileId(Integer jobId, Integer profileId);
}