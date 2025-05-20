package com.jobfind.dto.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class CompanyJobStatsDTO {
    private Integer companyId;
    private List<String> labels;
    private List<Long> counts;
}