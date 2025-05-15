package com.jobfind.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardInfoResponse {
    private String number;
    private String type;
    private String validToMonth;
    private String validToYear;
    private String ccv;
    private String postalCode;
}
