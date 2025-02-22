package com.jobfind.converters;

import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.models.Company;
import com.jobfind.populators.CompanyPopulator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CompanyConverter {
    private final CompanyPopulator companyPopulator;

    public CompanyDTO convertToCompanyDTO(Company company){
        CompanyDTO dto = new CompanyDTO();
        companyPopulator.populate(company, dto);
        return dto;
    }
}
