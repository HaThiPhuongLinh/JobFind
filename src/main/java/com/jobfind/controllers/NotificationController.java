package com.jobfind.controllers;

import com.jobfind.dto.dto.NotificationDTO;
import com.jobfind.dto.response.SuccessResponse;
import com.jobfind.services.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final INotificationService notificationServiceImpl;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(notificationServiceImpl.getNotificationsByUserId(userId));
    }

    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Long> countUnreadNotifications(@PathVariable Integer userId) {
        return ResponseEntity.ok(notificationServiceImpl.countUnreadNotifications(userId));
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<SuccessResponse> markAsRead(@PathVariable Integer notificationId) {
        notificationServiceImpl.markAsRead(notificationId);
        return ResponseEntity.ok(new SuccessResponse("Update read status successfully"));
    }

    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Integer userId) {
        notificationServiceImpl.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }
}
