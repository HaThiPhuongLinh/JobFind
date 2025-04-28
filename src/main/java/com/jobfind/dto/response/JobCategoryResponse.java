package com.jobfind.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCategoryResponse {
    private Integer jobCategoryId;
    private String name;
}
