package com.apartmentbuilding.PTIT.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@EntityListeners(value = AuditingEntityListener.class)
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column()
    private String id;
    @Column()
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToMany()
    @JoinTable(name = "notificationTargets",
    joinColumns = @JoinColumn(name = "notificationId"),
    inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<UserEntity> users;
    @Column()
    @CreatedDate()
    private Date createdDate;
    @Column(updatable = false)
    @CreatedBy()
    private String sender;

}
