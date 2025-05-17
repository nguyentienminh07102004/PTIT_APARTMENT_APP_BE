package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Request.Notification.NotificationRequest;
import com.apartmentbuilding.PTIT.DTO.Response.Notification.NotificationResponse;
import com.apartmentbuilding.PTIT.DTO.Response.Notification.NotificationUserResponse;
import com.apartmentbuilding.PTIT.Service.Notification.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/notifications")
public class NotificationController {
    private final INotificationService notificationService;

    @PostMapping()
    public ResponseEntity<NotificationResponse> saveAndNotify(@RequestBody NotificationRequest notificationRequest) {
        NotificationResponse notificationResponse = this.notificationService.save(notificationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationResponse);
    }

    @GetMapping(value = "/top-5-notifications")
    public ResponseEntity<List<NotificationUserResponse>> findTop5Notifications() {
        List<NotificationUserResponse> notifications = this.notificationService.findTop5NotificationNewest();
        return ResponseEntity.status(HttpStatus.OK).body(notifications);
    }

    @PutMapping(value = "/change-is-read/{notificationId}")
    public ResponseEntity<Void> changeIsRead(@PathVariable String notificationId) {
        this.notificationService.changeNotificationIsRead(notificationId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping()
    public ResponseEntity<PagedModel<NotificationResponse>> findAll(@RequestParam(required = false) Integer page,
                                                                    @RequestParam(required = false) Integer limit) {
        PagedModel<NotificationResponse> notifications = this.notificationService.findAll(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(notifications);
    }
}
