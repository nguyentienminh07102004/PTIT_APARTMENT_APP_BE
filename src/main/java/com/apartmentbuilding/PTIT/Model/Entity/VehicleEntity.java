package com.apartmentbuilding.PTIT.Model.Entity;

import com.apartmentbuilding.PTIT.Common.Enums.VehicleStatus;
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

@Entity
@Table(name = "vehicles")
@Getter
@Setter
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column()
    private String id;
    @ManyToOne
    @JoinColumn(name = "apartmentName", referencedColumnName = "name")
    private ApartmentEntity apartment;
    @Column(nullable = false, unique = true)
    private String licensePlate;
    @Column()
    @Enumerated(value = EnumType.STRING)
    private VehicleStatus status;

    @ManyToOne
    @JoinColumn(name = "typeName", referencedColumnName = "name")
    private VehicleTypeEntity type;
}
