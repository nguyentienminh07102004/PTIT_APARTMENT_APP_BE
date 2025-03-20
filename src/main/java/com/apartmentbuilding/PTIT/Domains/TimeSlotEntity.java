package com.apartmentbuilding.PTIT.Domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "timeSlots")
@Getter
@Setter
public class TimeSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "startTime", nullable = false)
    private String startTime;
    @Column(name = "endTime", nullable = false)
    private String endTime;

    @ManyToMany
    @JoinTable(name = "bookingTimeSlot",
    joinColumns = @JoinColumn(name = "timeSlotId"),
    inverseJoinColumns = @JoinColumn(name = "bookingId"))
    private List<BookingEntity> bookings;
}
