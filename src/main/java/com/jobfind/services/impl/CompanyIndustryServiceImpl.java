package com.jobfind.services.impl;

import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Industry;
import com.jobfind.repositories.IndustryRepository;
import com.jobfind.services.ICompanyIndustryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyIndustryServiceImpl implements ICompanyIndustryService {
    private final IndustryRepository industryRepository;

    @Override
    public List<Industry> getAllCompanyIndustries() {
        return industryRepository.findAll();
    }

    @Override
    public void addCompanyIndustry(String industryName) {
        if(industryRepository.existsByName(industryName)){
            throw new BadRequestException("Industry already exists");
        }

        Industry industry = Industry.builder()
                .name(industryName)
                .build();
        industryRepository.save(industry);
    }

    @Override
    public void deleteCompanyIndustry(Integer industryId) {
        industryRepository.deleteById(industryId);
    }

    @Override
    public void updateCompanyIndustry(Integer industryId, String industryName) {
        Industry industry = industryRepository.findById(industryId).orElseThrow(
                () -> new BadRequestException("Industry not found"));
        industry.setName(industryName);
        industryRepository.save(industry);
    }
}
