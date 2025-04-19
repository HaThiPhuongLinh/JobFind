package com.jobfind.populators;

import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.dto.response.IndustryReponse;
import com.jobfind.models.Company;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyPopulator {
    public void populate(Company source, CompanyDTO target) {
        target.setCompanyId(source.getCompanyId());
        target.setCompanyName(source.getCompanyName());
        target.setDescription(source.getDescription());
        target.setLogoPath(source.getLogoPath());
        List<IndustryReponse> industryReponse = source.getIndustry().stream()
                .map(industry -> IndustryReponse.builder()
                        .industryId(industry.getIndustryId())
                        .name(industry.getName())
                        .build())
                .toList();
        target.setIndustry(industryReponse);
        target.setWebsite(source.getWebsite());
        target.setEmail(source.getUser().getEmail());
        target.setPhoneNumber(source.getUser().getPhone());
    }
}

