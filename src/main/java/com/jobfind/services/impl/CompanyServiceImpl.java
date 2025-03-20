package com.jobfind.services.impl;

import com.jobfind.converters.CompanyConverter;
import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Company;
import com.jobfind.repositories.CompanyRepository;
import com.jobfind.services.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<CompanyDTO> findCompanyByIndustryAndCompanyName(Integer industryId, String companyName) {
        List<Company> company = companyRepository.findByIndustryOrCompanyName(industryId, companyName);
        return company.stream().map(companyConverter::convertToCompanyDTO).toList();
    }
}
