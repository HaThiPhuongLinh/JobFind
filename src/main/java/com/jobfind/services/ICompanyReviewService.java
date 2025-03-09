package com.jobfind.services;

import com.jobfind.dto.request.AddCompanyReviewRequest;
import com.jobfind.dto.request.UpdateCompanyReviewRequest;
import com.jobfind.dto.response.CompanyReviewResponse;

import java.util.List;

public interface ICompanyReviewService {
    void addCompanyReview(AddCompanyReviewRequest addCompanyReviewRequest);
    void updateCompanyReview(UpdateCompanyReviewRequest updateCompanyReviewRequest);
    void deleteCompanyReview(Integer reviewId);
    List<CompanyReviewResponse> getCompanyReviews(Integer companyId);
}
