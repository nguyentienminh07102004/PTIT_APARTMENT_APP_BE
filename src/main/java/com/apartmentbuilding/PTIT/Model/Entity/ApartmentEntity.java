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
    @Column()
    private String id;
    @Column()
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "double precision")
    private Double area;
    @ManyToOne
    @JoinColumn(referencedColumnName = "floorNumber")
    private FloorEntity floor;

    @ManyToOne
    @JoinColumn(name = "userEmail", referencedColumnName = "email")
    private UserEntity user;

    @OneToMany(mappedBy = "apartment")
    private List<BookingEntity> bookings;
}
