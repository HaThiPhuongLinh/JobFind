package com.jobfind.populators;

import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.models.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyPopulator {
    public void populate(Company source, CompanyDTO target) {
        target.setCompanyId(source.getCompanyId());
        target.setCompanyName(source.getCompanyName());
        target.setDescription(source.getDescription());
        target.setLogoPath(source.getLogoPath());
        target.setIndustry(source.getIndustry());
        target.setWebsite(source.getWebsite());
        target.setEmail(source.getUser().getEmail());
        target.setPhone(source.getUser().getPhone());
    }
}

