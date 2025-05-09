package com.jobfind.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationMeta {
    private Integer conversationId;
    private Integer senderId;
    private String senderName;
    private String senderAvatar;
    private Integer roleId;
    private int unreadCount;
}
