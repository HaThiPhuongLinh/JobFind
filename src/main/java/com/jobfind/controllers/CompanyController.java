package com.jobfind.controllers;

import com.jobfind.dto.dto.CompanyDTO;
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

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Integer companyId) {
        return ResponseEntity.ok(companyServiceImpl.getCompanyById(companyId));
    }

    @GetMapping("/searchCompany/{industryId}/{companyName}")
    public ResponseEntity<List<CompanyDTO>> searchCompanies(
            @RequestParam(required = false) Integer industryId,
            @RequestParam(required = false) String companyName) {
    return ResponseEntity.ok(companyServiceImpl.findCompanyByIndustryAndCompanyName(industryId, companyName));
    }
}
