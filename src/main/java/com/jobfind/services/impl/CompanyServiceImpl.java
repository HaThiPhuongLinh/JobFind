package com.jobfind.services.impl;

import com.jobfind.converters.CompanyConverter;
import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Company;
import com.jobfind.repositories.CompanyRepository;
import com.jobfind.services.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements ICompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;

    @Override
    public CompanyDTO getCompanyById(Integer companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BadRequestException("Company not found"));

        return companyConverter.convertToCompanyDTO(company);
    }
}
