package com.jobfind.converters;

import com.jobfind.dto.dto.JobSeekerProfileDTO;
import com.jobfind.models.JobSeekerProfile;
import com.jobfind.populators.JobSeekerProfilePopulator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JobSeekerProfileConverter {
    private final JobSeekerProfilePopulator jobSeekerProfilePopulator;

    public JobSeekerProfileDTO convertToJobSeekerProfileDTO(JobSeekerProfile jobSeekerProfile) {
        JobSeekerProfileDTO dto = new JobSeekerProfileDTO();
        jobSeekerProfilePopulator.populate(jobSeekerProfile, dto);
        return dto;
    }
}
