package chathub.com.repositories;

import chathub.com.models.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedJobRepository extends JpaRepository<SavedJob, Integer> {
}