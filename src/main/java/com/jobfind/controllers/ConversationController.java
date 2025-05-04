package com.jobfind.controllers;

import com.jobfind.dto.request.SendFileMessageRequest;
import com.jobfind.dto.request.SendTextMessageRequest;
import com.jobfind.dto.response.ConversationResponse;
import com.jobfind.dto.response.MessageResponse;
import com.jobfind.services.IConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ConversationController {
    @Autowired
    private IConversationService conversationServiceImpl;

    @PostMapping("/conversation")
    public ResponseEntity<ConversationResponse> createConversation(@RequestParam Integer jobSeekerId, @RequestParam Integer employerId) {
        ConversationResponse conversation = conversationServiceImpl.createConversation(jobSeekerId, employerId);
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

    @PostMapping("/message/text")
    public ResponseEntity<MessageResponse> sendTextMessage(@RequestBody SendTextMessageRequest request) {
        MessageResponse message = conversationServiceImpl.sendTextMessage(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/message/file")
    public ResponseEntity<MessageResponse> sendFileMessage(@RequestBody SendFileMessageRequest request) {
        MessageResponse message = conversationServiceImpl.sendFileMessage(request);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/messages/{conversationId}")
    public ResponseEntity<List<MessageResponse>> getMessagesByConversationId(@PathVariable Integer conversationId) {
        List<MessageResponse> messages = conversationServiceImpl.getMessagesByConversationId(conversationId);
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/messages/read")
    public ResponseEntity<Void> markMessagesAsRead(@RequestParam Integer conversationId, @RequestParam Integer userId) {
        conversationServiceImpl.markMessagesAsRead(conversationId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/conversations/unread/{userId}")
    public ResponseEntity<Long> countUnreadConversations(@PathVariable Integer userId) {
        Long unreadCount = conversationServiceImpl.countUnreadConversations(userId);
        return ResponseEntity.ok(unreadCount);
    }
}