package com.jobfind.populators;

import com.jobfind.dto.dto.ResumeDTO;
import com.jobfind.models.Resume;
import org.springframework.stereotype.Component;

@Component
public class ResumePopulator {
    public void populate(Resume source, ResumeDTO target) {
        source.setResumeId(target.getResumeId());
        source.setFileName(target.getFileName());
        source.setFilePath(target.getFilePath());
        source.setUploadedAt(target.getUploadedAt());
    }
}
