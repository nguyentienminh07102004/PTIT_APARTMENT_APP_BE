package com.apartmentbuilding.PTIT.Service.Notification;

import com.apartmentbuilding.PTIT.DTO.Request.Notification.NotificationRequest;
import com.apartmentbuilding.PTIT.DTO.Response.Notification.NotificationResponse;
import com.apartmentbuilding.PTIT.DTO.Response.Notification.NotificationUserResponse;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface INotificationService {
    NotificationResponse save(NotificationRequest notificationRequest);
    List<NotificationUserResponse> findTop5NotificationNewest();
    PagedModel<NotificationResponse> findAll(String search, Integer page, Integer limit);
    void changeNotificationIsRead(String notificationId);
    PagedModel<NotificationUserResponse> findAllNotificationUsers(String search, Integer page, Integer limit);
}
