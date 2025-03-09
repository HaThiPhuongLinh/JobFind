package com.jobfind.services.impl;

import com.jobfind.converters.JobSeekerProfileConverter;
import com.jobfind.dto.dto.JobSeekerProfileDTO;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Company;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.models.SavedJobSeeker;
import com.jobfind.repositories.CompanyRepository;
import com.jobfind.repositories.JobSeekerProfileRepository;
import com.jobfind.repositories.SavedJobSeekerRepository;
import com.jobfind.services.ISavedJobSeekerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavedJobSeekerServiceImpl implements ISavedJobSeekerService {
    private final SavedJobSeekerRepository savedJobSeekerRepository;
    private final JobSeekerProfileRepository jobSeekerRepository;
    private final CompanyRepository companyRepository;
    private final JobSeekerProfileConverter jobSeekerProfileConverter;

    @Override
    public void saveJobSeeker(Integer jobSeekerProfileId, Integer companyId) {
        JobSeekerProfile jobSeeker = jobSeekerRepository.findById(jobSeekerProfileId)
                .orElseThrow(() -> new BadRequestException("Job Seeker not found"));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BadRequestException("Company not found"));

        boolean alreadySaved = savedJobSeekerRepository.existsByJobSeekerProfileProfileIdAndCompanyCompanyId(jobSeekerProfileId, companyId);
        if (alreadySaved) {
            throw new BadRequestException("Job Seeker already saved by this company");
        }

        SavedJobSeeker savedJobSeeker = SavedJobSeeker.builder()
                .jobSeekerProfile(jobSeeker)
                .company(company)
                .savedAt(LocalDate.now())
                .build();

        savedJobSeekerRepository.save(savedJobSeeker);
    }

    @Override
    public void unsaveJobSeeker(Integer jobSeekerProfileId, Integer companyId) {
        JobSeekerProfile jobSeeker = jobSeekerRepository.findById(jobSeekerProfileId)
                .orElseThrow(() -> new BadRequestException("Job Seeker not found"));

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BadRequestException("Company not found"));

        SavedJobSeeker savedJobSeeker = savedJobSeekerRepository.findByJobSeekerProfileProfileIdAndCompanyCompanyId(jobSeekerProfileId, companyId)
                .orElseThrow(() -> new BadRequestException("Job Seeker not saved by this company"));

        savedJobSeekerRepository.delete(savedJobSeeker);
    }

    @Override
    public List<JobSeekerProfileDTO> getListSavedJobSeekers(Integer companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BadRequestException("Company not found"));

        List<SavedJobSeeker> savedJobSeekers = savedJobSeekerRepository.findByCompanyCompanyId(companyId);

        return savedJobSeekers.stream()
                .map(savedJobSeeker -> jobSeekerProfileConverter
                        .convertToJobSeekerProfileDTO(savedJobSeeker.getJobSeekerProfile()))
                .collect(Collectors.toList());
    }
}

