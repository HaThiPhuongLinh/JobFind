package com.jobfind.populators;

import com.jobfind.converters.ResumeConverter;
import com.jobfind.converters.SkillConverter;
import com.jobfind.converters.WorkExperienceConverter;
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
    private final WorkExperienceConverter workExperienceConverter;
    public void populate(JobSeekerProfile source, JobSeekerProfileDTO target) {
        target.setProfileId(source.getProfileId());
        target.setUserId(source.getUser().getUserId());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setTitle(source.getTitle());
        target.setResumeList(source.getResumes().stream().
                filter(resume -> !resume.isDeleted())
                .map(resumeConverter::convertToResumeDTO)
                .collect(Collectors.toList()));
        target.setAddress(source.getAddress());
        target.setAvatar(source.getAvatar());
        target.setEmail(source.getUser().getEmail());
        target.setPhone(source.getUser().getPhone());
        target.setBirthDay(source.getUser().getJobSeekerProfile().getBirthDay());
        target.setSkills(source.getSkills().stream()
                .map(skillConverter::convertToSkillDTO)
                .collect(Collectors.toList()));
        target.setWorkExperiences(source.getWorkExperiences().stream()
                .map(workExperienceConverter::convertToWorkExperienceDTO)
                .collect(Collectors.toList()));
    }
}
