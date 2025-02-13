package com.jobfind.converters;

import com.jobfind.dto.dto.WorkExperienceDTO;
import com.jobfind.models.WorkExperience;
import com.jobfind.populators.SkillPopulator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class WorkExperienceConverter {
    private final SkillPopulator skillPopulator;
    public void populate(WorkExperience source, WorkExperienceDTO targer) {
        targer.setCompanyName(source.getCompany().getCompanyName());
        targer.setStartDate(source.getStartDate().toString());
        targer.setEndDate(source.getEndDate().toString());
        targer.setJobTitle(source.getJobPosition().getName());
        targer.setJobType(source.getJobType());
        targer.setDescription(source.getDescription());
        targer.setSkills(source.getSkills().stream()
                .map(skillPopulator::convertToSkillDTO)
                .collect(Collectors.toList()));
    }
}
