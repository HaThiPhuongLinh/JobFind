package com.jobfind.populators;

import com.jobfind.dto.dto.ResumeDTO;
import com.jobfind.models.Resume;
import org.springframework.stereotype.Component;

@Component
public class ResumePopulator {
    public void populate(Resume source, ResumeDTO target) {
        source.setResumeId(target.getResumeId());
        source.setResumeName(target.getResumeName());
        source.setResumePath(target.getResumePath());
        source.setUploadedAt(target.getUploadedAt());
    }
}
