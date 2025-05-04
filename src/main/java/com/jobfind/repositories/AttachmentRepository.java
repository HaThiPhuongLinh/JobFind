package com.jobfind.repositories;

import com.jobfind.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    Attachment findByMessageId(Integer messageId);
}