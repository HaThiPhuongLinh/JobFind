package com.jobfind.converters;

import com.jobfind.dto.dto.ResumeDTO;
import com.jobfind.models.Resume;
import com.jobfind.populators.ResumePopulator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ResumeConverter {
    private final ResumePopulator resumePopulator;

    public ResumeDTO convertToResumeDTO(Resume resume) {
        ResumeDTO dto = new ResumeDTO();
        resumePopulator.populate(resume, dto);
        return dto;
    }
}