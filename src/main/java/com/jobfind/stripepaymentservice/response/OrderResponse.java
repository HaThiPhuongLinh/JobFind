package com.jobfind.stripepaymentservice.response;

import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.stripepaymentservice.models.enums.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Integer id;
    private String name;
    private String description;
    private Double totalPrice;
    private Long durationMonths;
    private PaymentStatus status;
    private String intentSecret;
    private String publishableKey;
    private CompanyDTO companyDTO;
    private PaymentDetailsResponse paymentInfo;
    private LocalDateTime createdAt;
}
