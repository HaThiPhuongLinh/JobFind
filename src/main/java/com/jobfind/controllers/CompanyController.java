package com.jobfind.controllers;

import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.dto.request.CardRequest;
import com.jobfind.dto.response.CardInfoResponse;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final ICompanyService companyServiceImpl;

    @GetMapping("/all")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        return ResponseEntity.ok(companyServiceImpl.getAllCompanies());
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Integer companyId) {
        return ResponseEntity.ok(companyServiceImpl.getCompanyById(companyId));
    }

    @GetMapping("/searchCompany")
    public ResponseEntity<List<CompanyDTO>> searchCompanies(
            @RequestParam(required = false) Integer industryId,
            @RequestParam(required = false) String companyName) {
        return ResponseEntity.ok(companyServiceImpl.findCompanyByIndustryAndCompanyName(industryId, companyName));
    }

    @PostMapping("/createCard")
    public ResponseEntity<SuccessResponse> createCardPaymentForCompany(@RequestBody CardRequest cardRequest) {
        companyServiceImpl.createCardPaymentForCompany(cardRequest);
        return ResponseEntity.ok(new SuccessResponse("Card payment created successfully"));
    }

    @GetMapping("/cardInfo/{userId}")
    public ResponseEntity<CardInfoResponse> getCardInfoByCompanyId(@PathVariable Integer userId) {
        return ResponseEntity.ok(companyServiceImpl.getCardInfoByCompanyId(userId));
    }
}
