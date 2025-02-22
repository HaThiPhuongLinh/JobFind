package com.jobfind.services;

import com.jobfind.dto.dto.CompanyDTO;

public interface ICompanyService {
    CompanyDTO getCompanyById(Integer companyId);
}
