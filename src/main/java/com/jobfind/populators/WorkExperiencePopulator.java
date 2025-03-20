package com.jobfind.populators;

import com.jobfind.dto.dto.WorkExperienceDTO;
import com.jobfind.models.JobCategory;
import com.jobfind.models.WorkExperience;
import com.jobfind.converters.SkillConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class WorkExperiencePopulator {
    private final SkillConverter skillConverter;
    public void populate(WorkExperience source, WorkExperienceDTO target) {
        target.setCompanyName(source.getCompany().getCompanyName());
        target.setStartDate(source.getStartDate().toString());
        target.setEndDate(source.getEndDate().toString());
        target.setJobTitle(source.getJobPosition().getName());
        target.setJobType(source.getJobType());
        target.setDescription(source.getDescription());
        target.setSkills(source.getSkills().stream()
                .map(skillConverter::convertToSkillDTO)
                .collect(Collectors.toList()));
        List<JobCategory> categoryDTOList = source.getCategories().stream()
                .map(category -> JobCategory.builder()
                        .jobCategoryId(category.getJobCategoryId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList());
        target.setCategories(categoryDTOList);
    }
}
