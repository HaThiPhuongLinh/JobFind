package jobfind.com.repositories;

import jobfind.com.models.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedJobRepository extends JpaRepository<SavedJob, Integer> {
}