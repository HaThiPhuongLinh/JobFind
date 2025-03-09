package com.jobfind.populators;

import com.jobfind.converters.ResumeConverter;
import com.jobfind.converters.SkillConverter;
import com.jobfind.dto.dto.JobSeekerProfileDTO;
import com.jobfind.models.JobSeekerProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JobSeekerProfilePopulator {
    private final SkillConverter skillConverter;
    private final ResumeConverter resumeConverter;
    public void populate(JobSeekerProfile source, JobSeekerProfileDTO target) {
        target.setProfileId(source.getProfileId());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setResumeList(source.getResumes().stream()
                .map(resumeConverter::convertToResumeDTO)
                .collect(Collectors.toList()));
        target.setAddress(source.getAddress());
        target.setEmail(source.getUser().getEmail());
        target.setPhone(source.getUser().getPhone());
        target.setSkills(source.getSkills().stream()
                .map(skillConverter::convertToSkillDTO)
                .collect(Collectors.toList()));
    }
}
