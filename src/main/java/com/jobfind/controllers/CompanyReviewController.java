package com.jobfind.controllers;

import com.jobfind.dto.request.AddCompanyReviewRequest;
import com.jobfind.dto.request.UpdateCompanyReviewRequest;
import com.jobfind.dto.response.CompanyReviewResponse;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.ICompanyReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company/review")
@RequiredArgsConstructor
public class CompanyReviewController {
    private final ICompanyReviewService companyReviewServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<SuccessResponse> addCompanyReview(@RequestBody AddCompanyReviewRequest addCompanyReviewRequest) {
        companyReviewServiceImpl.addCompanyReview(addCompanyReviewRequest);
        return ResponseEntity.ok(new SuccessResponse("Company review added successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessResponse> updateCompanyReview(@RequestBody UpdateCompanyReviewRequest updateCompanyReviewRequest) {
        companyReviewServiceImpl.updateCompanyReview(updateCompanyReviewRequest);
        return ResponseEntity.ok(new SuccessResponse("Company review updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse> deleteCompanyReview(Integer reviewId) {
        companyReviewServiceImpl.deleteCompanyReview(reviewId);
        return ResponseEntity.ok(new SuccessResponse("Company review deleted successfully"));
    }

    @GetMapping("/getListReviews/{companyId}")
    public ResponseEntity<List<CompanyReviewResponse>> getCompanyReviews(Integer companyId) {
        List<CompanyReviewResponse> companyReviewResponse = companyReviewServiceImpl.getCompanyReviews(companyId);
        return ResponseEntity.ok(companyReviewResponse);
    }
}
