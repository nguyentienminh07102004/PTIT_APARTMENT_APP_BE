package com.apartmentbuilding.PTIT.Mapper.Notification;

import com.apartmentbuilding.PTIT.DTO.Request.Notification.NotificationRequest;
import com.apartmentbuilding.PTIT.DTO.Response.Notification.NotificationResponse;
import com.apartmentbuilding.PTIT.Model.Entity.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface INotificationMapper {
    NotificationEntity requestToEntity(NotificationRequest request);
    NotificationResponse entityToResponse(NotificationEntity notificationEntity);
}
