package com.apartmentbuilding.PTIT.Domains;

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
@Table(name = "buildings")
@Getter
@Setter
public class BuildingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "building")
    private List<ApartmentEntity> apartments;
}
