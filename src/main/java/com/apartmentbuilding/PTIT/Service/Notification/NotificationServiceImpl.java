package com.apartmentbuilding.PTIT.Service.Notification;

import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Request.Notification.NotificationRequest;
import com.apartmentbuilding.PTIT.DTO.Response.Notification.NotificationResponse;
import com.apartmentbuilding.PTIT.DTO.Response.Notification.NotificationUserResponse;
import com.apartmentbuilding.PTIT.Mapper.Notification.NotificationConvertor;
import com.apartmentbuilding.PTIT.Model.Entity.NotificationEntity;
import com.apartmentbuilding.PTIT.Model.Entity.NotificationEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.NotificationTargetEntity;
import com.apartmentbuilding.PTIT.Model.Entity.NotificationTargetEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.UserEntity_;
import com.apartmentbuilding.PTIT.Repository.INotificationRepository;
import com.apartmentbuilding.PTIT.Repository.INotificationTargetRepository;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
        Pageable pageable = PageRequest.of(0, 5);
        Page<NotificationTargetEntity> notifications = this.notificationTargetRepository.findByUser_EmailAndIsReadFalseOrderByNotification_CreatedDate(SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
        return notifications
                .stream()
                .map(this.notificationMapper::toResponse)
                .toList();
    }

    @Override
    public PagedModel<NotificationResponse> findAll(String search, Integer page, Integer limit) {
        Pageable pageable = PaginationUtils.pagination(page, limit, Sort.by(Sort.Direction.DESC, NotificationEntity_.CREATED_DATE));
        Page<NotificationEntity> notifications = this.notificationRepository.findAll(pageable);
        return new PagedModel<>(notifications.map(this.notificationMapper::toResponse));
    }

    @Override
    @Transactional
    public void changeNotificationIsRead(String notificationId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        NotificationTargetEntity notification = this.notificationTargetRepository.findByNotification_IdAndUser_Email(notificationId, email)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.NOTIFICATION_NOT_FOUND));
        notification.setIsRead(!notification.getIsRead().equals(Boolean.TRUE));
        this.notificationTargetRepository.save(notification);
    }

    @Override
    public PagedModel<NotificationUserResponse> findAllNotificationUsers(String search, Integer page, Integer limit) {
        Specification<NotificationTargetEntity> specification = (root, query, builder) -> {
            try {
                String email = SecurityContextHolder.getContext().getAuthentication().getName();
                if (!StringUtils.hasText(search)) {
                    return builder.equal(root.get(NotificationTargetEntity_.USER).get(UserEntity_.EMAIL), email);
                }
                List<Predicate> predicates = new ArrayList<>();

                if (search.matches("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/([0-9]{4})$")) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date startDate = format.parse(search);
                    Date endDate = Date.from(startDate.toInstant().plus(1, ChronoUnit.DAYS));
                    predicates.add(builder.and(builder.greaterThanOrEqualTo(root.get(NotificationTargetEntity_.NOTIFICATION).get(NotificationEntity_.CREATED_DATE), startDate),
                            builder.lessThan(root.get(NotificationTargetEntity_.NOTIFICATION).get(NotificationEntity_.CREATED_DATE), endDate)));
                } else if (search.matches("^(0?[1-9]|1[0-2])/([0-9]{4})$")) {
                    SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
                    Date startDate = format.parse(search);
                    Date endDate = Date.from(startDate.toInstant().plus(1, ChronoUnit.MONTHS));
                    predicates.add(builder.and(builder.greaterThanOrEqualTo(root.get(NotificationTargetEntity_.NOTIFICATION).get(NotificationEntity_.CREATED_DATE), startDate),
                            builder.lessThan(root.get(NotificationTargetEntity_.NOTIFICATION).get(NotificationEntity_.CREATED_DATE), endDate)));
                }
                predicates.add(builder.like(builder.lower(root.get(NotificationTargetEntity_.NOTIFICATION).get(NotificationEntity_.TITLE)), "%" + search.toLowerCase() + "%"));
                if (query != null) {
                    query.orderBy(builder.desc(root.get(NotificationTargetEntity_.NOTIFICATION).get(NotificationEntity_.CREATED_DATE)));
                }
                return builder.and(builder.or(predicates.toArray(new Predicate[0])), builder.equal(root.get(NotificationTargetEntity_.USER).get(UserEntity_.EMAIL), email));
            } catch (Exception exception) {
                return builder.conjunction();
            }
        };
        Pageable pageable = PaginationUtils.pagination(page, limit);
        Page<NotificationTargetEntity> notifications = this.notificationTargetRepository.findAll(specification, pageable);
        return new PagedModel<>(notifications.map(this.notificationMapper::toResponse));

    }
}
