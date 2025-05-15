package com.jobfind.services.impl;

import com.jobfind.converters.CompanyConverter;
import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.dto.request.CardRequest;
import com.jobfind.dto.response.CardInfoResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.CardCompany;
import com.jobfind.models.Company;
import com.jobfind.models.User;
import com.jobfind.repositories.CardCompanyRepository;
import com.jobfind.repositories.CompanyRepository;
import com.jobfind.repositories.UserRepository;
import com.jobfind.services.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements ICompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CardCompanyRepository cardCompanyRepository;
    private final CompanyConverter companyConverter;

    @Override
    public List<CompanyDTO> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(companyConverter::convertToCompanyDTO).toList();
    }

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

    @Override
    public void createCardPaymentForCompany(CardRequest cardRequest) {
        if (cardRequest.getNumber() == null || cardRequest.getType() == null ||
                cardRequest.getValidToMonth() == null || cardRequest.getValidToYear() == null ||
                cardRequest.getUserId() == null) {
            throw new BadRequestException("Invalid card request");
        }

        User user = userRepository.findById(cardRequest.getUserId())
                .orElseThrow(() -> new BadRequestException("User not found"));

        CardCompany cardCompany = CardCompany.builder()
                .number(cardRequest.getNumber())
                .type(cardRequest.getType())
                .validToMonth(cardRequest.getValidToMonth())
                .validToYear(cardRequest.getValidToYear())
                .ccv(cardRequest.getCcv())
                .postalCode(cardRequest.getPostalCode())
                .user(user)
                .build();

        cardCompanyRepository.save(cardCompany);
    }

    @Override
    public CardInfoResponse getCardInfoByCompanyId(Integer userId) {
        CardCompany cardCompany = cardCompanyRepository.findByUserUserId(userId);
        if (cardCompany == null) {
            throw new BadRequestException("Card not found for user");
        }

        return CardInfoResponse.builder()
                .number(cardCompany.getNumber())
                .type("card")
                .validToMonth(cardCompany.getValidToMonth())
                .validToYear(cardCompany.getValidToYear())
                .ccv(cardCompany.getCcv())
                .postalCode(cardCompany.getPostalCode())
                .build();
    }
}
