package com.jobfind.services;

import com.jobfind.dto.request.SendFileMessageRequest;
import com.jobfind.dto.request.SendTextMessageRequest;
import com.jobfind.dto.response.ConversationMeta;
import com.jobfind.dto.response.ConversationResponse;
import com.jobfind.dto.response.MessageResponse;

import java.util.List;

public interface IConversationService {
    ConversationResponse createConversation(Integer jobSeekerId, Integer employerId);
    List<ConversationResponse> getUserConversations(Integer userId);
    ConversationResponse getConversationById(Integer conversationId, Integer userId);
    ConversationMeta getConversationMetaById(Integer conversationId, Integer userId);
    MessageResponse sendTextMessage(SendTextMessageRequest sendTextMessageRequest);
    MessageResponse sendFileMessage(SendFileMessageRequest sendFileMessageRequest);
    List<MessageResponse> getMessagesByConversationId(Integer conversationId);
    void markMessagesAsRead(Integer conversationId, Integer userId);
    Long countUnreadConversations(Integer userId);
}
