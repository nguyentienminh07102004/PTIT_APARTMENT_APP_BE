package com.apartmentbuilding.PTIT.Service.Notification;

import com.apartmentbuilding.PTIT.DTO.Request.Notification.NotificationRequest;
import com.apartmentbuilding.PTIT.DTO.Response.NotificationResponse;

public interface INotificationService {
    NotificationResponse save(NotificationRequest notificationRequest);
    void sendNotification(NotificationRequest notificationRequest);
}
