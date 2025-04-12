package com.apartmentbuilding.PTIT.Service.Notification;

import com.apartmentbuilding.PTIT.DTO.Response.NotificationResponse;
import com.apartmentbuilding.PTIT.Repository.INotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {
    private final INotificationRepository notificationRepository;

    public ResponseEntity save() {
        return null;
    }
}
