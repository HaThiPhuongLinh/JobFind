package jobfind.com.repositories;

import jobfind.com.models.CompanyReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Integer> {
}