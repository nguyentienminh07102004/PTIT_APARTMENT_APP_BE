package com.apartmentbuilding.PTIT.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "vehicleTypes")
@Getter
@Setter
public class VehicleTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column()
    private String id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column()
    private Double unitPrice;
    @OneToMany(mappedBy = "type")
    private List<VehicleEntity> vehicles;
}
