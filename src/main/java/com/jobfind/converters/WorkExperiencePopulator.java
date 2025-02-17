package com.jobfind.converters;

import com.jobfind.dto.dto.WorkExperienceDTO;
import com.jobfind.models.WorkExperience;
import com.jobfind.populators.SkillConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    }
}
