package com.jobfind.converters;

import com.jobfind.dto.dto.NotificationDTO;
import com.jobfind.models.Notification;
import com.jobfind.populators.NotificationPopulator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class NotificationConverter {
    private final NotificationPopulator notificationPopulator;

    public NotificationDTO convertToNotificationDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        notificationPopulator.populate(notification, dto);
        return dto;
    }
}
