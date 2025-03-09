package com.jobfind.services.impl;

import com.jobfind.dto.dto.JobDTO;
import com.jobfind.dto.request.CreateJobRequest;
import com.jobfind.dto.request.UpdateJobRequest;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Company;
import com.jobfind.models.Job;
import com.jobfind.models.JobCategory;
import com.jobfind.models.Skill;
import com.jobfind.converters.JobConverter;
import com.jobfind.repositories.CompanyRepository;
import com.jobfind.repositories.JobCategoryRepository;
import com.jobfind.repositories.JobRepository;
import com.jobfind.repositories.SkillRepository;
import com.jobfind.services.IJobService;
import com.jobfind.utils.ValidateField;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements IJobService {
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final SkillRepository skillRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final ValidateField validateField;
    private final JobConverter jobConverter;

    @Override
    public void createJob(CreateJobRequest request, BindingResult bindingResult) {
        Map<String, String> errors = validateField.getErrors(bindingResult);

        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        Company company = companyRepository.findById(request.getCompanyId()).orElseThrow(() -> new BadRequestException("Company not found"));

        List<Skill> skills = skillRepository.findAllById(request.getSkillIds());
        if (skills.size() != request.getSkillIds().size()) {
            throw new BadRequestException("Some skills are invalid");
        }

        List<JobCategory> categories = jobCategoryRepository.findAllById(request.getCategoryIds());
        if (categories.size() != request.getCategoryIds().size()) {
            throw new BadRequestException("Some categories are invalid");
        }

        Job job = Job.builder()
                .company(company)
                .title(request.getTitle())
                .description(request.getDescription())
                .requirements(request.getRequirements())
                .benefits(request.getBenefits())
                .salaryMin(request.getSalaryMin())
                .salaryMax(request.getSalaryMax())
                .jobType(request.getJobType())
                .location(request.getLocation())
                .postedAt(request.getPostedAt())
                .deadline(request.getDeadline())
                .isActive(true)
                .skills(skills)
                .categories(categories)
                .build();

        jobRepository.save(job);
    }

    @Override
    public void updateJob(UpdateJobRequest request, BindingResult bindingResult) {
        Map<String, String> errors = validateField.getErrors(bindingResult);

        if (!errors.isEmpty()) {
            throw new BadRequestException("Please complete all required fields to proceed.", errors);
        }

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new BadRequestException("Job not found"));

        List<Skill> skills = skillRepository.findAllById(request.getSkillIds());
        if (skills.size() != request.getSkillIds().size()) {
            throw new BadRequestException("Some skills are invalid");
        }

        List<JobCategory> categories = jobCategoryRepository.findAllById(request.getCategoryIds());
        if (categories.size() != request.getCategoryIds().size()) {
            throw new BadRequestException("Some categories are invalid");
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequirements(request.getRequirements());
        job.setBenefits(request.getBenefits());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setJobType(request.getJobType());
        job.setLocation(request.getLocation());
        job.setDeadline(request.getDeadline());
        job.setIsActive(request.getIsActive());
        job.setSkills(skills);
        job.setCategories(categories);

        jobRepository.save(job);
    }

    @Override
    public void deleteJob(Integer jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new BadRequestException("Job not found"));

        job.setIsDeleted(true);
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobByID(Integer jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() ->
                new BadRequestException("Job not found"));

        return jobConverter.convertToJobDTO(job);
    }

    @Override
    public List<JobDTO> searchJobs(String keyword, String location, Integer jobCategoryId) {

        if(StringUtils.isEmpty(keyword) && StringUtils.isEmpty(location)) {
            return jobRepository.findAll().stream().map(jobConverter::convertToJobDTO).toList();
        }

        List<Job> jobs = jobRepository.searchJobs(keyword, location, jobCategoryId);
        return jobs.stream().map(jobConverter::convertToJobDTO).toList();
    }

    @Override
    public List<JobDTO> getJobsByCompanyId(Integer companyId) {
        return jobRepository.findByCompanyCompanyId(companyId).stream().map(jobConverter::convertToJobDTO).toList();
    }
}