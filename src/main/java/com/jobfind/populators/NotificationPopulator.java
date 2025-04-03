package com.jobfind.populators;

import com.jobfind.dto.dto.NotificationDTO;
import com.jobfind.models.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationPopulator {
    public void populate(Notification source, NotificationDTO target) {
        target.setNotiId(source.getNotificationId());
        target.setContent(source.getContent());
        target.setIsRead(source.getIsRead());
        target.setCreatedAt(source.getCreatedAt());
        target.setUserId(source.getUser().getUserId());
        target.setApplicationId(source.getApplication().getApplicationId());
    }
}
