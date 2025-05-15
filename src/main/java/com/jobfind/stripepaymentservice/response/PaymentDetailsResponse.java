package com.jobfind.stripepaymentservice.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsResponse {
    private String id;
    private String cardNumber;
    private String startMonth;
    private String startYear;
    private String expiryMonth;
    private String expiryYear;
    private String subscriptionId;
}
