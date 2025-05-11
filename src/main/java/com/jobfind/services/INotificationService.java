package com.jobfind.services;

import com.jobfind.dto.dto.NotificationDTO;
import com.jobfind.dto.request.CreateNotiRequest;
import com.jobfind.models.Notification;

import java.util.List;

public interface INotificationService {
    List<NotificationDTO> getNotificationsByUserId(Integer userId);
    NotificationDTO createNoti(CreateNotiRequest request);
    Long countUnreadNotifications(Integer userId);
    void markAsRead(Integer notificationId);
    void markAllAsRead(Integer userId);
}
