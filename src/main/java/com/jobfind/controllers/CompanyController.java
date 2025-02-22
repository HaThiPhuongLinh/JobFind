package com.jobfind.controllers;

import com.jobfind.dto.dto.CompanyDTO;
import com.jobfind.services.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {
    private final ICompanyService companyServiceImpl;

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Integer companyId) {
        return ResponseEntity.ok(companyServiceImpl.getCompanyById(companyId));
    }
}
