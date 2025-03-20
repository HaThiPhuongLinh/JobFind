package com.jobfind.services;

import com.jobfind.dto.dto.CompanyDTO;

import java.util.List;

public interface ICompanyService {
    CompanyDTO getCompanyById(Integer companyId);
    List<CompanyDTO> findCompanyByIndustryAndCompanyName(Integer industryId, String companyName);
}
