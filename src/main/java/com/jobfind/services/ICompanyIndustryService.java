package com.jobfind.services;

import com.jobfind.dto.response.IndustryReponse;
import com.jobfind.models.Industry;

import java.util.List;

public interface ICompanyIndustryService {
    List<IndustryReponse> getAllCompanyIndustries();
    void addCompanyIndustry(String industryName);
    void deleteCompanyIndustry(Integer industryId);
    void updateCompanyIndustry(Integer industryId, String industryName);
}
