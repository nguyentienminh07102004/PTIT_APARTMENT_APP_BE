package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.NotificationTargetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INotificationTargetRepository extends JpaRepository<NotificationTargetEntity, String> {
    Optional<NotificationTargetEntity> findByNotification_IdAndUser_Email(String id, String email);
    List<NotificationTargetEntity> findTop5ByUser_EmailOrderByNotification_CreatedDate(String email);
}
