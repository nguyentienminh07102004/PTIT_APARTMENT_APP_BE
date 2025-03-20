package com.apartmentbuilding.PTIT.Domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "apartmentId")
    private ApartmentEntity apartment;
    @Column(name = "licensePlate")
    private String licensePlate;

    @ManyToOne
    @JoinColumn(name = "type")
    private VehicleTypeEntity type;
}
