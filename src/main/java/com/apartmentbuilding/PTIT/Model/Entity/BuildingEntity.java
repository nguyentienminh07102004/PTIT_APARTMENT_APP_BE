package com.apartmentbuilding.PTIT.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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
    @Column()
    private String address;
    @Column()
    @Temporal(TemporalType.DATE)
    private Date buildAt;
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "building")
    private List<ApartmentEntity> apartments;
}
