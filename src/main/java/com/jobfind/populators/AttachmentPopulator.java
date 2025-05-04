package com.jobfind.populators;

import com.jobfind.dto.response.AttachmentResponse;
import com.jobfind.models.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentPopulator {
    public void populate(Attachment source, AttachmentResponse target) {
        target.setAttachmentId(source.getId());
        target.setFileName(source.getFileName());
        target.setFilePath(source.getFilePath());
        target.setFileType(source.getFileType());
        target.setUploadedAt(source.getUploadTime());
    }
}
