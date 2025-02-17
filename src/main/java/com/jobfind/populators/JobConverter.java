package com.jobfind.populators;

import com.jobfind.dto.dto.JobDTO;
import com.jobfind.models.Job;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JobConverter {
    private final com.jobfind.converters.JobPopulator jobPopulator;

    public JobDTO convertToJobDTO(Job job) {
        JobDTO dto = new JobDTO();
        jobPopulator.populate(job, dto);
        return dto;
    }
}
