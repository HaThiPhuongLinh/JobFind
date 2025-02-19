package com.jobfind.repositories;

import com.jobfind.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findByJobJobIdAndJobSeekerProfileProfileId(Integer jobId, Integer profileId);
}