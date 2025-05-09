package com.jobfind.services.impl;

import com.jobfind.constants.JobFindConstant;
import com.jobfind.converters.MessageConverter;
import com.jobfind.dto.request.SendFileMessageRequest;
import com.jobfind.dto.request.SendTextMessageRequest;
import com.jobfind.dto.response.ConversationMeta;
import com.jobfind.dto.response.ConversationResponse;
import com.jobfind.dto.response.MessageResponse;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Attachment;
import com.jobfind.models.Conversation;
import com.jobfind.models.Message;
import com.jobfind.models.User;
import com.jobfind.models.enums.MessageType;
import com.jobfind.models.enums.Role;
import com.jobfind.repositories.*;
import com.jobfind.services.IConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements IConversationService {
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;
    private final MessageConverter messageConverter;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public ConversationResponse createConversation(Integer jobSeekerId, Integer employerId) {
        Conversation conversation = Conversation.builder()
                .jobSeeker(userRepository.findById(jobSeekerId).orElseThrow(() -> new BadRequestException("User not found")))
                .company(userRepository.findById(employerId).orElseThrow(() -> new BadRequestException("User not found")))
                .createAt(LocalDateTime.now())
                .lastMessageAt(LocalDateTime.now())
                .unreadCountJobSeeker(0)
                .unreadCountCompany(0)
                .build();

        Conversation savedConversation = conversationRepository.save(conversation);

        return ConversationResponse.builder()
                .conversationId(savedConversation.getId())
                .createdAt(savedConversation.getCreateAt())
                .lastMessageAt(savedConversation.getLastMessageAt())
                .lastMessage("")
                .unreadCount(0)
                .build();
    }

    @Override
    public List<ConversationResponse> getUserConversations(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found"));

        List<Conversation> conversations;

        if (user.getRole() == Role.JOBSEEKER) {
            conversations = conversationRepository.findByJobSeeker(user);
        } else if (user.getRole() == Role.COMPANY) {
            conversations = conversationRepository.findByCompany(user);
        } else {
            throw new BadRequestException("Unsupported role");
        }

        return conversations.stream().map(conversation -> {
            User otherUser = conversation.getJobSeeker().getUserId().equals(userId)
                    ? conversation.getCompany()
                    : conversation.getJobSeeker();

            Message lastMessage = messageRepository.findTopByConversationIdOrderBySentAtDesc(conversation.getId());

            int unreadCount = conversation.getJobSeeker().getUserId().equals(userId)
                    ? conversation.getUnreadCountJobSeeker()
                    : conversation.getUnreadCountCompany();

            return ConversationResponse.builder()
                    .conversationId(conversation.getId())
                    .createdAt(conversation.getCreateAt())
                    .lastMessageAt(conversation.getLastMessageAt())
                    .lastMessage(lastMessage != null ? lastMessage.getContent() : "")
                    .senderId(lastMessage != null ? lastMessage.getSender().getUserId() : null)
                    .roleId(otherUser.getRole() == Role.JOBSEEKER ? otherUser.getJobSeekerProfile().getProfileId() : otherUser.getCompany().getCompanyId())
                    .senderName(otherUser.getRole() == Role.JOBSEEKER ? otherUser.getJobSeekerProfile().getFirstName() + " " + otherUser.getJobSeekerProfile().getLastName() : otherUser.getCompany().getCompanyName())
                    .senderAvatar(otherUser.getRole() == Role.JOBSEEKER ? otherUser.getJobSeekerProfile().getAvatar() : otherUser.getCompany().getLogoPath())
                    .unreadCount(unreadCount)
                    .build();
        }).toList();
    }

    @Override
    public ConversationResponse getConversationById(Integer conversationId, Integer userId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new BadRequestException("Conversation not found"));

        User jobSeeker = conversation.getJobSeeker();
        User company = conversation.getCompany();

        boolean isCurrentUserJobSeeker = jobSeeker.getUserId().equals(userId);
        User otherUser = isCurrentUserJobSeeker ? company : jobSeeker;
        Message lastMessage = messageRepository.findTopByConversationIdOrderBySentAtDesc(conversation.getId());
        int unreadCount = conversation.getJobSeeker().getUserId().equals(userId)
                ? conversation.getUnreadCountJobSeeker()
                : conversation.getUnreadCountCompany();

        return ConversationResponse.builder()
                .conversationId(conversation.getId())
                .createdAt(conversation.getCreateAt())
                .lastMessageAt(conversation.getLastMessageAt())
                .lastMessage(lastMessage != null ? lastMessage.getContent() : "")
                .senderId(otherUser.getUserId())
                .roleId(otherUser.getRole() == Role.JOBSEEKER ? otherUser.getJobSeekerProfile().getProfileId() : otherUser.getCompany().getCompanyId())
                .senderName(otherUser.getCompany() != null
                        ? otherUser.getCompany().getCompanyName()
                        : otherUser.getJobSeekerProfile().getFirstName() + " " + otherUser.getJobSeekerProfile().getLastName())
                .senderAvatar(otherUser.getCompany() != null
                        ? otherUser.getCompany().getLogoPath()
                        : otherUser.getJobSeekerProfile().getAvatar())
                .unreadCount(unreadCount)
                .build();
    }

    @Override
    public ConversationMeta getConversationMetaById(Integer conversationId, Integer userId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new BadRequestException("Conversation not found"));
        User jobSeeker = conversation.getJobSeeker();
        User company = conversation.getCompany();

        boolean isCurrentUserJobSeeker = jobSeeker.getUserId().equals(userId);
        User otherUser = isCurrentUserJobSeeker ? company : jobSeeker;
        int unreadCount = isCurrentUserJobSeeker
                ? conversation.getUnreadCountJobSeeker()
                : conversation.getUnreadCountCompany();

        return ConversationMeta.builder()
                .conversationId(conversation.getId())
                .senderId(otherUser.getUserId())
                .roleId(otherUser.getRole() == Role.JOBSEEKER ? otherUser.getJobSeekerProfile().getProfileId() : otherUser.getCompany().getCompanyId())
                .senderName(otherUser.getCompany() != null
                        ? otherUser.getCompany().getCompanyName()
                        : otherUser.getJobSeekerProfile().getFirstName() + " " + otherUser.getJobSeekerProfile().getLastName())
                .senderAvatar(otherUser.getCompany() != null
                        ? otherUser.getCompany().getLogoPath()
                        : otherUser.getJobSeekerProfile().getAvatar())
                .unreadCount(unreadCount)
                .build();
    }

    @Override
    public MessageResponse sendTextMessage(SendTextMessageRequest sendTextMessageRequest) {
        Conversation conversation = conversationRepository.findById(sendTextMessageRequest.getConversationId()).orElseThrow();
        Message message = Message.builder()
                .conversation(conversation)
                .sender(userRepository.findById(sendTextMessageRequest.getSenderId()).orElseThrow(() -> new BadRequestException("User not found")))
                .content(sendTextMessageRequest.getContent())
                .sentAt(LocalDateTime.now())
                .isRead(false)
                .messageType(MessageType.TEXT)
                .build();

        messageRepository.save(message);

        Integer recipientId = conversation.getJobSeeker().getUserId().equals(sendTextMessageRequest.getSenderId())
                ? conversation.getCompany().getUserId()
                : conversation.getJobSeeker().getUserId();

        if (conversation.getJobSeeker().getUserId().equals(sendTextMessageRequest.getSenderId())) {
            conversation.setUnreadCountCompany(conversation.getUnreadCountCompany() + 1);
        } else {
            conversation.setUnreadCountJobSeeker(conversation.getUnreadCountJobSeeker() + 1);
        }

        conversation.setLastMessageAt(LocalDateTime.now());
        conversationRepository.save(conversation);
//        ConversationMeta meta = getConversationMetaById(conversation.getId(), sendTextMessageRequest.getSenderId());

        Long totalUnread = conversationRepository.countUnreadConversations(recipientId);
        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_COUNT_UNREAD + recipientId, totalUnread);
//        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_DATA_CONVERSATION + recipientId, meta);
//        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_DATA_CONVERSATION + conversation.getId(), meta);
        return messageConverter.convertToMessageResponse(message);
    }

    @Override
    public MessageResponse sendFileMessage(SendFileMessageRequest sendFileMessageRequest) {
        Conversation conversation = conversationRepository.findById(sendFileMessageRequest.getConversationId()).orElseThrow();
        Message message = Message.builder()
                .conversation(conversation)
                .sender(userRepository.findById(sendFileMessageRequest.getSenderId()).orElseThrow(() -> new BadRequestException("User not found")))
                .content(sendFileMessageRequest.getContent())
                .sentAt(LocalDateTime.now())
                .isRead(false)
                .messageType(sendFileMessageRequest.getFileType().startsWith("image") ? MessageType.IMAGE : MessageType.FILE)
                .build();

        Message savedMessage = messageRepository.save(message);

        conversation.setLastMessageAt(LocalDateTime.now());
        conversationRepository.save(conversation);

        Attachment attachment = Attachment.builder()
                .fileName(sendFileMessageRequest.getFileName())
                .fileType(sendFileMessageRequest.getFileType())
                .filePath(sendFileMessageRequest.getFilePath())
                .message(message)
                .uploadTime(LocalDateTime.now())
                .build();

        Integer recipientId = conversation.getJobSeeker().getUserId().equals(sendFileMessageRequest.getSenderId())
                ? conversation.getCompany().getUserId()
                : conversation.getJobSeeker().getUserId();
        if (conversation.getJobSeeker().getUserId().equals(sendFileMessageRequest.getSenderId())) {
            conversation.setUnreadCountCompany(conversation.getUnreadCountCompany() + 1);
        } else {
            conversation.setUnreadCountJobSeeker(conversation.getUnreadCountJobSeeker() + 1);
        }
//        ConversationMeta meta = getConversationMetaById(conversation.getId(), sendFileMessageRequest.getSenderId());

        attachmentRepository.save(attachment);
        Long totalUnread = conversationRepository.countUnreadConversations(recipientId);
        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_COUNT_UNREAD + recipientId, totalUnread);
//        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_DATA_CONVERSATION + recipientId, meta);
//        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_DATA_CONVERSATION + conversation.getId(), meta);

        return messageConverter.convertToMessageResponse(savedMessage);
    }

    @Override
    public List<MessageResponse> getMessagesByConversationId(Integer conversationId) {
        List<Message> messages = messageRepository.findByConversationIdOrderBySentAtAsc(conversationId);
        return messages.stream()
                .map(messageConverter::convertToMessageResponse)
                .toList();
    }

    @Override
    public void markMessagesAsRead(Integer conversationId, Integer userId) {
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow();
        List<Message> messages = messageRepository.findByConversationIdOrderBySentAtAsc(conversationId);
        for (Message message : messages) {
            if (!message.getIsRead() && !message.getSender().getUserId().equals(userId)) {
                message.setIsRead(true);
                messageRepository.save(message);
            }
        }

        Integer otherUserId = conversation.getJobSeeker().getUserId().equals(userId)
                ? conversation.getCompany().getUserId()
                : conversation.getJobSeeker().getUserId();

        if (conversation.getJobSeeker().getUserId().equals(userId)) {
            conversation.setUnreadCountJobSeeker(0);
        } else {
            conversation.setUnreadCountCompany(0);
        }
//        ConversationMeta meta = getConversationMetaById(conversation.getId(), userId);
//        ConversationMeta otherMeta = getConversationMetaById(conversation.getId(), otherUserId);

        Long totalUnread = conversationRepository.countUnreadConversations(userId);
        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_COUNT_UNREAD + userId, totalUnread);
//        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_DATA_CONVERSATION + userId, meta);
//        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_DATA_CONVERSATION + otherMeta, meta);
//        messagingTemplate.convertAndSend(JobFindConstant.WS_TOPIC_DATA_CONVERSATION + conversationId, meta);
        conversationRepository.save(conversation);
    }

    @Override
    public Long countUnreadConversations(Integer userId) {
        return conversationRepository.countUnreadConversations(userId);
    }
}