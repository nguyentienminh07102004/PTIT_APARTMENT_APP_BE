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
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Checks;

import java.util.List;

@Entity
@Table(name = "apartments")
@Getter
@Setter
@Checks(value = {
        @Check(constraints = "floor > 0"),
        @Check(constraints = "area > 0")
})
public class ApartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "unitNumber")
    private String unitNumber;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Column(name = "area", columnDefinition = "double precision")
    private Double area;
    @Column(name = "floor", columnDefinition = "smallint")
    private Integer floor;
    @ManyToOne
    @JoinColumn(name = "buildingId")
    private BuildingEntity building;

    @ManyToOne
    @JoinColumn(name = "userEmail", referencedColumnName = "email")
    private UserEntity user;

    @OneToMany(mappedBy = "apartment")
    private List<BookingEntity> bookings;
}
