package com.apartmentbuilding.PTIT.Mapper.Notification;

import com.apartmentbuilding.PTIT.DTO.Request.Notification.NotificationRequest;
import com.apartmentbuilding.PTIT.DTO.Response.Notification.NotificationResponse;
import com.apartmentbuilding.PTIT.DTO.Response.Notification.NotificationUserResponse;
import com.apartmentbuilding.PTIT.Model.Entity.NotificationEntity;
import com.apartmentbuilding.PTIT.Model.Entity.NotificationTargetEntity;
import com.apartmentbuilding.PTIT.Service.User.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationConvertor {
    private final INotificationMapper notificationMapper;
    private final IUserService userService;

    public NotificationResponse toResponse(NotificationEntity notification) {
        NotificationResponse notificationResponse = this.notificationMapper.entityToResponse(notification);
        notificationResponse.setUsers(notification.getNotificationTargets()
                .stream()
                .collect(Collectors.toMap(notificationTarget -> notificationTarget.getUser().getEmail(), NotificationTargetEntity::getIsRead))
        );
        return notificationResponse;
    }

    public NotificationUserResponse toResponse(NotificationTargetEntity notification) {
        NotificationEntity notificationEntity = notification.getNotification();
        return NotificationUserResponse.builder()
                .id(notificationEntity.getId())
                .title(notificationEntity.getTitle())
                .content(notificationEntity.getContent())
                .createdDate(notificationEntity.getCreatedDate())
                .isRead(notification.getIsRead())
                .sender(notificationEntity.getSender())
                .build();
    }

    public NotificationEntity toEntity(NotificationRequest notificationRequest) {
        NotificationEntity notificationEntity = this.notificationMapper.requestToEntity(notificationRequest);
        List<NotificationTargetEntity> notificationTargets = notificationRequest.getRecipients()
                .stream()
                .map(this.userService::getUserByEmail)
                .map(user -> new NotificationTargetEntity(user, notificationEntity))
                .toList();
        notificationEntity.setNotificationTargets(notificationTargets);
        return notificationEntity;
    }
}