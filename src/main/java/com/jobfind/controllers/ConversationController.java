package com.jobfind.controllers;

import com.jobfind.constants.JobFindConstant;
import com.jobfind.dto.request.MarkMessagesReadRequest;
import com.jobfind.dto.request.SendFileMessageRequest;
import com.jobfind.dto.request.SendTextMessageRequest;
import com.jobfind.dto.response.ConversationResponse;
import com.jobfind.dto.response.MessageResponse;
import com.jobfind.services.IConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ConversationController {
    @Autowired
    private IConversationService conversationServiceImpl;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/conversation")
    public ResponseEntity<ConversationResponse> createConversation(@RequestParam Integer jobSeekerId, @RequestParam Integer employerId) {
        ConversationResponse conversation = conversationServiceImpl.createConversation(jobSeekerId, employerId);
        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_DATA_CONVERSATION + jobSeekerId, conversation);
        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_DATA_CONVERSATION + employerId, conversation);
        return ResponseEntity.ok(conversation);
    }

    @GetMapping("/conversations/{userId}")
    public ResponseEntity<List<ConversationResponse>> getUserConversations(@PathVariable Integer userId) {
        List<ConversationResponse> conversations = conversationServiceImpl.getUserConversations(userId);
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<ConversationResponse> getConversation(
            @PathVariable Integer conversationId,
            @RequestParam Integer userId) {

        ConversationResponse conversation = conversationServiceImpl.getConversationById(conversationId, userId);
        return ResponseEntity.ok(conversation);
    }

    @MessageMapping("/message/text")
    public void sendTextMessage(@RequestBody SendTextMessageRequest request) {
        MessageResponse message = conversationServiceImpl.sendTextMessage(request);
        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_CONVERSATION + request.getConversationId(), message);
    }

    @MessageMapping("/message/file")
    public void sendFileMessage(@RequestBody SendFileMessageRequest request) {
        MessageResponse message = conversationServiceImpl.sendFileMessage(request);
        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_CONVERSATION + request.getConversationId(), message);
    }

    @GetMapping("/messages/{conversationId}")
    public ResponseEntity<List<MessageResponse>> getMessagesByConversationId(@PathVariable Integer conversationId) {
        List<MessageResponse> messages = conversationServiceImpl.getMessagesByConversationId(conversationId);
        return ResponseEntity.ok(messages);
    }

    @MessageMapping("/messages/read")
    public void markMessagesAsRead(@RequestBody MarkMessagesReadRequest request) {
        conversationServiceImpl.markMessagesAsRead(request.getConversationId(), request.getUserId());
    }

    @GetMapping("/conversations/unread/{userId}")
    public ResponseEntity<Long> countUnreadConversations(@PathVariable Integer userId) {
        Long unreadCount = conversationServiceImpl.countUnreadConversations(userId);
        return ResponseEntity.ok(unreadCount);
    }

    @GetMapping("/conversationIdByUserIdAndEmployerId")
    public ResponseEntity<Long> findConversationIdByUserIdAndEmployerId(@RequestParam Integer userId, @RequestParam Integer employerId) {
        Long conversationId = conversationServiceImpl.findConversationIdByUserIdAndEmployerId(userId, employerId);
        return ResponseEntity.ok(conversationId);
    }
}