package com.jobfind.repositories;

import com.jobfind.models.ApplicationStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationStatusHistoryRepository extends JpaRepository<ApplicationStatusHistory, Integer> {
    List<ApplicationStatusHistory> findByApplicationApplicationId(Integer applicationId);
}