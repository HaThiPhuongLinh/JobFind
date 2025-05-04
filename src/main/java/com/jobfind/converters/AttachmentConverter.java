package com.jobfind.converters;

import com.jobfind.dto.response.AttachmentResponse;
import com.jobfind.models.Attachment;
import com.jobfind.populators.AttachmentPopulator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AttachmentConverter {
    private final AttachmentPopulator attachmentPopulator;
    public AttachmentResponse convertToAttachmentDTO(Attachment attachment) {
        AttachmentResponse dto = new AttachmentResponse();
         attachmentPopulator.populate(attachment, dto);
         return dto;
    }
}
