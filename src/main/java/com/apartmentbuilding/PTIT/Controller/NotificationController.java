package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Request.Notification.NotificationRequest;
import com.apartmentbuilding.PTIT.DTO.Response.NotificationResponse;
import com.apartmentbuilding.PTIT.Service.Notification.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/notifications")
public class NotificationController {
    private final INotificationService notificationService;

    @PostMapping(value = "/notification")
    public ResponseEntity<NotificationResponse> saveAndNotify(@RequestBody NotificationRequest notificationRequest) {
        NotificationResponse notificationResponse = this.notificationService.save(notificationRequest);
        this.notificationService.sendNotification("/topic/notification", notificationResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationResponse);
    }
}
