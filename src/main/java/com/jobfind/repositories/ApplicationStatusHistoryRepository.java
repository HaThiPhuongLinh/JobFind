package com.jobfind.repositories;

import com.jobfind.models.ApplicationStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationStatusHistoryRepository extends JpaRepository<ApplicationStatusHistory, Integer> {
}