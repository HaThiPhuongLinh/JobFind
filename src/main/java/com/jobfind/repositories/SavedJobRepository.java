package com.jobfind.repositories;

import com.jobfind.models.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedJobRepository extends JpaRepository<SavedJob, Integer> {
    Optional<SavedJob> findByJobJobIdAndJobSeekerProfileProfileId(Integer jobId, Integer profileId);
    List<SavedJob> findByJobSeekerProfileProfileId(Integer profileId);
}