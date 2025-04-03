package com.jobfind.services;

import com.jobfind.dto.dto.NotificationDTO;
import com.jobfind.dto.request.CreateNotiRequest;

import java.util.List;

public interface INotificationService {
    List<NotificationDTO> getAllNotifications();
    NotificationDTO createNoti(CreateNotiRequest request);
    void updateReadStatus(Integer notiId);
}
