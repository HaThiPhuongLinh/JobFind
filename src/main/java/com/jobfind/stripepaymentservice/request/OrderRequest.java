package com.jobfind.stripepaymentservice.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private Integer userId;
    private Integer subscriptionPlanId;
    private boolean isPayByCC;
}
