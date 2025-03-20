package com.jobfind.services;

import com.jobfind.models.Industry;

import java.util.List;

public interface ICompanyIndustryService {
    List<Industry> getAllCompanyIndustries();
    void addCompanyIndustry(String industryName);
    void deleteCompanyIndustry(Integer industryId);
    void updateCompanyIndustry(Integer industryId, String industryName);
}
