package com.jobfind.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarkMessagesReadRequest {
    private Integer conversationId;
    private Integer userId;
}
