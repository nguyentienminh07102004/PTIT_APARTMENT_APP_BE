package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<NotificationEntity, String> {
    List<NotificationEntity> findTop5ByNotificationTargets_User_EmailOrderByCreatedDateDesc(String email);
}
