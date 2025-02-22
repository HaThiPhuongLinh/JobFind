package com.jobfind.converters;

import com.jobfind.dto.dto.SkillDTO;
import com.jobfind.models.Skill;
import com.jobfind.populators.SkillPopulator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SkillConverter {
    private final SkillPopulator skillPopulator;

    public SkillDTO convertToSkillDTO(Skill skill) {
        SkillDTO dto = new SkillDTO();
        skillPopulator.populate(skill, dto);
        return dto;
    }
}