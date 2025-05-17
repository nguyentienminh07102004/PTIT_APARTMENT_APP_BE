package com.apartmentbuilding.PTIT.Model.Entity;

import com.apartmentbuilding.PTIT.Common.Enum.AmenityStatus;
import com.apartmentbuilding.PTIT.Common.Enum.AmenityType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "amenities")
@Getter
@Setter
public class AmenityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column()
    private String id;
    @Column()
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column()
    @Enumerated(value = EnumType.STRING)
    private AmenityType type;
    @Column()
    @Enumerated(value = EnumType.STRING)
    private AmenityStatus status;
    @Column()
    private Time openTime;
    @Column()
    private Time closeTime;
    @Column()
    private Boolean requiresBooking;
    @Column()
    private int maxCapacity;
    @Column()
    private String location;
    @Column()
    private Double usageFee;
    @Column()
    private String imageUrl;
    @Column()
    private Date lastMaintenanceDate;
    @Column()
    private String notes;
    @Column()
    private Boolean isBookable = false;
}
