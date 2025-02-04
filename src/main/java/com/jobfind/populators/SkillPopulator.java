package com.jobfind.populators;

import com.jobfind.converters.SkillConverter;
import com.jobfind.dto.dto.SkillDTO;
import com.jobfind.models.Skill;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SkillPopulator {

    private final SkillConverter skillConverter;

    public SkillDTO convertToSkillDTO(Skill skill) {
        SkillDTO dto = new SkillDTO();
        skillConverter.populate(skill, dto);
        return dto;
    }
}