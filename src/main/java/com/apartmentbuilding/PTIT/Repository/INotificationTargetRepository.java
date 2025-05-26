package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.NotificationTargetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface INotificationTargetRepository extends JpaRepository<NotificationTargetEntity, String>, JpaSpecificationExecutor<NotificationTargetEntity> {
    Optional<NotificationTargetEntity> findByNotification_IdAndUser_Email(String id, String email);
    Page<NotificationTargetEntity> findByUser_EmailAndIsReadFalseOrderByNotification_CreatedDate(String email, Pageable pageable);
}
