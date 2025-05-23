package com.jobfind.dto.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChartDataDTO {
    private List<String> labels;
    private List<Long> counts;
}