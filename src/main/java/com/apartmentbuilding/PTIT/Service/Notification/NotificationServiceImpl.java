package com.apartmentbuilding.PTIT.Service.Notification;

import com.apartmentbuilding.PTIT.DTO.Request.Notification.NotificationRequest;
import com.apartmentbuilding.PTIT.DTO.Response.NotificationResponse;
import com.apartmentbuilding.PTIT.Mapper.Notification.INotificationMapper;
import com.apartmentbuilding.PTIT.Model.Entity.NotificationEntity;
import com.apartmentbuilding.PTIT.Repository.INotificationRepository;
import com.apartmentbuilding.PTIT.Service.User.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {
    private final INotificationRepository notificationRepository;
    private final IUserService userService;
    private final INotificationMapper notificationMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public NotificationResponse save(NotificationRequest notificationRequest) {
        NotificationEntity notification = this.notificationMapper.requestToEntity(notificationRequest);
        notification.setUser(this.userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        this.notificationRepository.save(notification);
        return this.notificationMapper.entityToResponse(notification);
    }

    @Override
    public void sendNotification(String destination, NotificationResponse notificationResponse) {
        this.messagingTemplate.convertAndSend(destination, notificationResponse);
    }
}
