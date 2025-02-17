package com.jobfind.converters;

import com.jobfind.dto.dto.SkillDTO;
import com.jobfind.models.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillPopulator {
    public void populate(Skill source, SkillDTO target) {
        target.setName(source.getName());
    }
}
