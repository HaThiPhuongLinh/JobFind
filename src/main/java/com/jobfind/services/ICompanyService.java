package com.jobfind.services;

import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.dto.request.CardRequest;
import com.jobfind.dto.response.CardInfoResponse;

import java.util.List;

public interface ICompanyService {
    List<CompanyDTO> getAllCompanies();
    CompanyDTO getCompanyById(Integer companyId);
    List<CompanyDTO> findCompanyByIndustryAndCompanyName(Integer industryId, String companyName);

    void createCardPaymentForCompany(CardRequest cardRequest);

    CardInfoResponse getCardInfoByCompanyId(Integer userId);

}
