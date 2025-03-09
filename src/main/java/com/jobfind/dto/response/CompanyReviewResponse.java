package com.jobfind.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyReviewResponse {
    private Integer reviewId;
    private Integer rating;
    private String reviewText;
    private LocalDateTime reviewDate;
    private Integer companyId;
    private Integer jobSeekerProfileId;
    private String jobSeekerProfileName;
}
