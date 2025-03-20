package com.jobfind.repositories;

import com.jobfind.models.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustryRepository extends JpaRepository<Industry, Integer> {
    boolean existsByName(String name);
}
