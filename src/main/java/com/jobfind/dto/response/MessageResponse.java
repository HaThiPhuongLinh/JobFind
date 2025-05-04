package com.jobfind.dto.response;

import com.jobfind.models.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Integer messageId;
    private Integer conversationId;
    private Integer senderId;
    private String senderName;
    private String content;
    private LocalDateTime sentAt;
    private Boolean isRead;
    private MessageType messageType;
    private AttachmentResponse attachment;
}