package com.jobfind.services.impl;

import com.jobfind.dto.request.AddCompanyReviewRequest;
import com.jobfind.dto.request.UpdateCompanyReviewRequest;
import com.jobfind.dto.response.CompanyReviewResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Company;
import com.jobfind.models.CompanyReview;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.enums.ApplicationStatus;
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

//        boolean hasWorkedAtCompany = jobSeeker.getWorkExperiences().stream()
//                .anyMatch(exp -> exp.getCompany().getCompanyId().equals(company.getCompanyId()));
//
//        boolean hasInterviewedAtCompany = jobSeeker.getApplications().stream()
//                .anyMatch(app -> app.getJob().getCompany().getCompanyId().equals(company.getCompanyId()) &&
//                        (app.getApplicationStatus() == ApplicationStatus.INTERVIEWING || app.getApplicationStatus() == ApplicationStatus.HIRED
//                          || app.getApplicationStatus() == ApplicationStatus.REJECTED));
//
//        boolean hasOnlyApplied = jobSeeker.getApplications().stream()
//                .anyMatch(app -> app.getJob().getCompany().getCompanyId().equals(company.getCompanyId()) &&
//                        (app.getApplicationStatus() == ApplicationStatus.PENDING || app.getApplicationStatus() == ApplicationStatus.REVIEWING));
//
//        if (!hasWorkedAtCompany && !hasInterviewedAtCompany) {
//            throw new BadRequestException("You can only review a company you have worked for or interviewed with.");
//        }
//
//        if (hasOnlyApplied) {
//            throw new BadRequestException("You cannot review a company if you have only applied but never interviewed.");
//        }

        CompanyReview review = CompanyReview.builder()
                .company(company)
                .jobSeekerProfile(jobSeeker)
                .rating(addCompanyReviewRequest.getRating())
                .reviewText(addCompanyReviewRequest.getReview())
                .reviewDate(LocalDateTime.now())
                .isApproved(false)
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
        review.setIsApproved(false);

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

        //only return approved reviews
        reviews = reviews.stream().filter(CompanyReview::getIsApproved).collect(Collectors.toList());

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

