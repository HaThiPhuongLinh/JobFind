package com.jobfind.controllers;

import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final INotificationService notificationServiceImpl;

    @PutMapping("/read/{notiId}")
    public ResponseEntity<SuccessResponse> updateReadStatus(Integer notiId) {
        notificationServiceImpl.updateReadStatus(notiId);
        return ResponseEntity.ok(new SuccessResponse("Update read status successfully"));
    }
}
