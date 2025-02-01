package com.jobfind.converters;

import com.jobfind.dto.dto.SkillDTO;
import com.jobfind.models.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillConverter {
    public void populate(Skill source, SkillDTO targer) {
        targer.setName(source.getName());
    }
}
