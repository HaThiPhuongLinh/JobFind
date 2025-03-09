package com.jobfind.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCompanyReviewRequest {
    private Integer reviewId;
    private Integer rating;
    private String reviewText;
}
