package com.jobfind.populators;

import com.jobfind.converters.AttachmentConverter;
import com.jobfind.dto.response.MessageResponse;
import com.jobfind.models.Attachment;
import com.jobfind.models.Message;
import com.jobfind.repositories.AttachmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MessagePopulator {
    private final AttachmentRepository attachmentRepository;
    private final AttachmentConverter attachmentConverter;
    public void populate(Message source, MessageResponse target) {
        target.setMessageId(source.getId());
        target.setSenderId(source.getSender().getUserId());
        target.setSenderName(source.getSender().getJobSeekerProfile() != null
                ? source.getSender().getJobSeekerProfile().getFirstName() + " " +
                source.getSender().getJobSeekerProfile().getLastName()
                : source.getSender().getCompany() != null
                ? source.getSender().getCompany().getCompanyName()
                : "Unknown");
        target.setContent(source.getContent());
        target.setSentAt(source.getSentAt());

        Attachment attachment = attachmentRepository.findByMessageId(source.getId());
        if (attachment != null) {
            target.setAttachment(attachmentConverter.convertToAttachmentDTO(attachment));
        } else {
            target.setAttachment(null);
        }

        target.setMessageType(source.getMessageType());
        target.setIsRead(source.getIsRead());
        target.setConversationId(source.getConversation().getId());
    }

}
