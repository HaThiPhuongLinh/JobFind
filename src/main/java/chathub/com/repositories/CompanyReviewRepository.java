package chathub.com.repositories;

import chathub.com.models.CompanyReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Integer> {
}