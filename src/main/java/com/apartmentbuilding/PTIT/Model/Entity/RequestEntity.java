package com.apartmentbuilding.PTIT.Model.Entity;

import com.apartmentbuilding.PTIT.Common.Enums.RequestStatus;
import com.apartmentbuilding.PTIT.Common.Enums.RequestType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "requests")
@Getter
@Setter
@EntityListeners(value = AuditingEntityListener.class)
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String title;
    private String content;
    @CreatedDate
    private Date createdDate;
    @CreatedBy
    private String createdBy;
    private String image;
    @Enumerated(value = EnumType.STRING)
    private RequestStatus status;
    @Enumerated(value = EnumType.STRING)
    private RequestType type;

    @ManyToOne
    @JoinColumn(name = "apartmentName", referencedColumnName = "name")
    private ApartmentEntity apartment;
}
