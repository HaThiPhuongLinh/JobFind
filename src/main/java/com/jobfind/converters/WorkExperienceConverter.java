package com.jobfind.converters;

import com.jobfind.dto.dto.WorkExperienceDTO;
import com.jobfind.models.WorkExperience;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class WorkExperienceConverter {
    private final com.jobfind.populators.WorkExperiencePopulator workExperiencePopulator;

    public WorkExperienceDTO convertToWorkExperienceDTO(WorkExperience workExperience) {
        WorkExperienceDTO dto = new WorkExperienceDTO();
        workExperiencePopulator.populate(workExperience, dto);
        return dto;
    }
}
