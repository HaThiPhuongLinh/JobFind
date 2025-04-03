package com.jobfind.dto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class NotificationDTO {
    private Integer notiId;
    private String content;
    private Boolean isRead;
    private Integer applicationId;
    private Integer userId;
    private LocalDateTime createdAt;
}
