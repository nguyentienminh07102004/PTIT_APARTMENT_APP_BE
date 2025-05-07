package com.apartmentbuilding.PTIT.Model.Entity;


import com.apartmentbuilding.PTIT.Common.Enum.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Getter
@Setter
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column()
    private String id;
    @ManyToOne
    @JoinColumn(name = "amenityId")
    private AmenityEntity amenity;
    @ManyToOne
    @JoinColumn(name = "apartmentId")
    private ApartmentEntity apartment;
    @Column()
    private Time startTime;
    @Column()
    private Time endTime;
    @Column()
    @Enumerated(value = EnumType.STRING)
    private BookingStatus status;
    @Column()
    private Date date;
    @Column()
    private Date bookingAt;
}
