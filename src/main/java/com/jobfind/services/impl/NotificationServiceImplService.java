package com.jobfind.services.impl;

import com.jobfind.converters.NotificationConverter;
import com.jobfind.dto.dto.NotificationDTO;
import com.jobfind.dto.request.CreateNotiRequest;
import com.jobfind.exception.BadRequestException;
import com.jobfind.models.Application;
import com.jobfind.models.Notification;
import com.jobfind.models.User;
import com.jobfind.repositories.ApplicationRepository;
import com.jobfind.repositories.NotificationRepository;
import com.jobfind.repositories.UserRepository;
import com.jobfind.services.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImplService implements INotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final NotificationConverter notificationConverter;
    @Override
    public List<NotificationDTO> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(notificationConverter::convertToNotificationDTO)
                .toList();
    }

    @Override
    public NotificationDTO createNoti(CreateNotiRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        Optional<Application> application = applicationRepository.findById(request.getApplicationId());
        Notification notification = Notification.builder()
                .user(user.orElseThrow(() -> new BadRequestException("User not found")))
                .application(application.orElseThrow(() -> new BadRequestException("Application not found")))
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();
        notificationRepository.save(notification);
        return notificationConverter.convertToNotificationDTO(notification);
    }

    @Override
    public void updateReadStatus(Integer notiId) {
        Notification notification = notificationRepository.findById(notiId)
                .orElseThrow(() -> new BadRequestException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
}
