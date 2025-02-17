package com.jobfind.populators;

import com.jobfind.dto.dto.JobSeekerProfileDTO;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.converters.SkillConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JobSeekerProfilePopulator {
    private final SkillConverter skillConverter;
    public void populate(JobSeekerProfile source, JobSeekerProfileDTO target) {
        target.setProfileId(source.getProfileId());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setResumeList(source.getResumes());
        target.setAddress(source.getAddress());
        target.setEmail(source.getUser().getEmail());
        target.setPhone(source.getUser().getPhone());
        target.setSkills(source.getSkills().stream()
                .map(skillConverter::convertToSkillDTO)
                .collect(Collectors.toList()));
    }
}
