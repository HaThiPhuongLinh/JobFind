package com.jobfind.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttachmentResponse {
    private Integer attachmentId;
    private String fileName;
    private String filePath;
    private String fileType;
    private LocalDateTime uploadedAt;
}