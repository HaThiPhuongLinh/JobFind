package com.jobfind.services;

import com.jobfind.dto.dto.CompanyDTO;

import java.util.List;

public interface ICompanyService {
    List<CompanyDTO> getAllCompanies();
    CompanyDTO getCompanyById(Integer companyId);
    List<CompanyDTO> findCompanyByIndustryAndCompanyName(Integer industryId, String companyName);
}
