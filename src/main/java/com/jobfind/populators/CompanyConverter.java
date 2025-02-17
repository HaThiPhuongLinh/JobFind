package com.jobfind.populators;

import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.models.Company;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CompanyConverter {
    private final com.jobfind.converters.CompanyPopulator companyPopulator;

    public CompanyDTO convertToCompanyDTO(Company company){
        CompanyDTO dto = new CompanyDTO();
        companyPopulator.populate(company, dto);
        return dto;
    }
}
