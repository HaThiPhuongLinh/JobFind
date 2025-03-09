package com.jobfind.services.impl;

import com.jobfind.dto.request.AddCompanyReviewRequest;
import com.jobfind.dto.request.UpdateCompanyReviewRequest;
import com.jobfind.dto.response.CompanyReviewResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Company;
import com.jobfind.models.CompanyReview;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.repositories.CompanyRepository;
import com.jobfind.repositories.CompanyReviewRepository;
import com.jobfind.repositories.JobSeekerProfileRepository;
import com.jobfind.services.ICompanyReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyReviewServiceImpl implements ICompanyReviewService {
    private final CompanyReviewRepository companyReviewRepository;
    private final CompanyRepository companyRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    @Override
    public void addCompanyReview(AddCompanyReviewRequest addCompanyReviewRequest) {
        if (companyReviewRepository.findByCompanyCompanyIdAndJobSeekerProfileProfileId(addCompanyReviewRequest.getCompanyId(), addCompanyReviewRequest.getJobSeekerId()).isPresent()) {
            throw new BadRequestException("Review already exists");
        }

        Company company = companyRepository.findById(addCompanyReviewRequest.getCompanyId())
                .orElseThrow(() -> new BadRequestException("Company not found"));

        JobSeekerProfile jobSeeker = jobSeekerProfileRepository.findById(addCompanyReviewRequest.getJobSeekerId())
                .orElseThrow(() -> new BadRequestException("Job Seeker Profile not found"));

        CompanyReview review = CompanyReview.builder()
                .company(company)
                .jobSeekerProfile(jobSeeker)
                .rating(addCompanyReviewRequest.getRating())
                .reviewText(addCompanyReviewRequest.getReview())
                .reviewDate(LocalDateTime.now())
                .build();

        companyReviewRepository.save(review);
    }

    @Override
    public void updateCompanyReview(UpdateCompanyReviewRequest updateCompanyReviewRequest) {
        CompanyReview review = companyReviewRepository.findById(updateCompanyReviewRequest.getReviewId())
                .orElseThrow(() -> new BadRequestException("Review not found"));

        review.setRating(updateCompanyReviewRequest.getRating());
        review.setReviewText(updateCompanyReviewRequest.getReviewText());
        review.setReviewDate(LocalDateTime.now());

        companyReviewRepository.save(review);
    }

    @Override
    public void deleteCompanyReview(Integer reviewId) {
        CompanyReview review = companyReviewRepository.findById(reviewId)
                .orElseThrow(() -> new BadRequestException("Review not found"));
        companyReviewRepository.delete(review);
    }

    @Override
    public List<CompanyReviewResponse> getCompanyReviews(Integer companyId) {
        List<CompanyReview> reviews = companyReviewRepository.findByCompanyCompanyId(companyId);
        return reviews.stream()
                .map(review -> CompanyReviewResponse.builder()
                        .reviewId(review.getReviewId())
                        .companyId(review.getCompany().getCompanyId())
                        .rating(review.getRating())
                        .reviewText(review.getReviewText())
                        .reviewDate(review.getReviewDate())
                        .jobSeekerProfileId(review.getJobSeekerProfile().getProfileId())
                        .jobSeekerProfileName(review.getJobSeekerProfile().getFirstName().concat(" ").concat(review.getJobSeekerProfile().getLastName()))
                        .build(
                ))
                .collect(Collectors.toList());
    }
}

