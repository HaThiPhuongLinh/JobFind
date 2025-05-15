package com.jobfind.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {
    private String number;
    private String type;
    private String validToMonth;
    private String validToYear;
    private String ccv;
    private String postalCode;
    private Integer userId;
}
