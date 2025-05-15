package com.jobfind.populators;

import com.jobfind.dto.dto.ResumeDTO;
import com.jobfind.models.Resume;
import org.springframework.stereotype.Component;

@Component
public class ResumePopulator {
    public void populate(Resume source, ResumeDTO target) {
        target.setResumeId(source.getResumeId());
        target.setResumeName(source.getResumeName());
        target.setResumePath(source.getResumePath());
        target.setUploadedAt(source.getUploadedAt());
        target.setDeleted(source.isDeleted());
    }
}
