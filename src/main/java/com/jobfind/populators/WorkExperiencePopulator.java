package com.jobfind.populators;

import com.jobfind.converters.WorkExperienceConverter;
import com.jobfind.dto.dto.WorkExperienceDTO;
import com.jobfind.models.WorkExperience;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class WorkExperiencePopulator {
    private final WorkExperienceConverter workExperienceConverter;

    public WorkExperienceDTO convertToWorkExperienceDTO(WorkExperience workExperience) {
        WorkExperienceDTO dto = new WorkExperienceDTO();
        workExperienceConverter.populate(workExperience, dto);
        return dto;
    }
}
