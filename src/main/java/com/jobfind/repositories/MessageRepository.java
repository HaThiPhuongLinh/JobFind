package com.jobfind.repositories;

import com.jobfind.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByConversationIdOrderBySentAtAsc(Integer conversationId);

    Message findTopByConversationIdOrderBySentAtDesc(Integer conversationId);
}