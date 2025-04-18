package com.apartmentbuilding.PTIT.Model.Entity;

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
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Checks;

@Entity
@Table(name = "vehicleInvoices")
@Getter
@Setter
@Checks(value = {@Check(constraints = "unitPrice > 0")})
public class VehicleInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "unitPrice")
    private Double unitPrice;
    @ManyToOne
    @JoinColumn(name = "monthlyInvoiceId")
    private MonthlyInvoiceEntity monthlyInvoice;

    @ManyToOne()
    @JoinColumn(name = "vehicleLicensePlate", referencedColumnName = "licensePlate")
    private VehicleEntity vehicle;
}