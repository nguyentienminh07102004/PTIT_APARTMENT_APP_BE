package com.apartmentbuilding.PTIT.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "floors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FloorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column()
    private String id;
    @Column(unique = true, nullable = false)
    private String floorName;
    @Column()
    private String description;

    @ManyToOne
    @JoinColumn(name = "buildingName", referencedColumnName = "name")
    private BuildingEntity building;

    @OneToMany(mappedBy = "floor")
    private List<ApartmentEntity> apartments;
}
