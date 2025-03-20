package com.jobfind.services;

import com.jobfind.models.Skill;

import java.util.List;

public interface ISkillService {
    List<Skill> getAllSkills();
    void addSkill(String skillName);
    void deleteSkill(Integer skillId);
    void updateSkill(Integer skillId, String skillName);
}
