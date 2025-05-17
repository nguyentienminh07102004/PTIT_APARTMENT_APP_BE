package com.apartmentbuilding.PTIT.Service.Notification;

import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Request.Notification.NotificationRequest;
import com.apartmentbuilding.PTIT.DTO.Response.Notification.NotificationResponse;
import com.apartmentbuilding.PTIT.DTO.Response.Notification.NotificationUserResponse;
import com.apartmentbuilding.PTIT.Mapper.Notification.NotificationConvertor;
import com.apartmentbuilding.PTIT.Model.Entity.NotificationEntity;
import com.apartmentbuilding.PTIT.Model.Entity.NotificationTargetEntity;
import com.apartmentbuilding.PTIT.Repository.INotificationRepository;
import com.apartmentbuilding.PTIT.Repository.INotificationTargetRepository;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {
    private final INotificationRepository notificationRepository;
    private final NotificationConvertor notificationMapper;
    private final INotificationTargetRepository notificationTargetRepository;

    @Override
    public NotificationResponse save(NotificationRequest notificationRequest) {
        NotificationEntity notification = this.notificationMapper.toEntity(notificationRequest);
        this.notificationRepository.save(notification);
        return this.notificationMapper.toResponse(notification);
    }

    @Override
    public List<NotificationUserResponse> findTop5NotificationNewest() {
        List<NotificationTargetEntity> notifications = this.notificationTargetRepository.findTop5ByUser_EmailOrderByNotification_CreatedDate(SecurityContextHolder.getContext().getAuthentication().getName());
        return notifications
                .stream()
                .map(this.notificationMapper::toResponse)
                .toList();
    }

    @Override
    public PagedModel<NotificationResponse> findAll(Integer page, Integer limit) {
        Pageable pageable = PaginationUtils.pagination(page, limit);
        Page<NotificationEntity> notifications = this.notificationRepository.findAll(pageable);
        return new PagedModel<>(notifications.map(this.notificationMapper::toResponse));
    }

    @Override
    @Transactional
    public void changeNotificationIsRead(String notificationId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        NotificationTargetEntity notification = this.notificationTargetRepository.findByNotification_IdAndUser_Email(notificationId, email)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.NOTIFICATION_NOT_FOUND));
        notification.setIsRead(true);
        this.notificationTargetRepository.save(notification);
    }
}
