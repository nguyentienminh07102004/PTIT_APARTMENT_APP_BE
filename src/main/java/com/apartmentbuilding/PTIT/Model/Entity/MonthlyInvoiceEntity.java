package com.apartmentbuilding.PTIT.Model.Entity;

import com.apartmentbuilding.PTIT.Common.Enum.PaymentStatus;
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

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "monthlyInvoice")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @ManyToOne
    @JoinColumn(name = "apartmentId")
    private ApartmentEntity apartment;
    @Column(name = "paidDate")
    private Date paidDate;
    @Column(name = "billingTime")
    private String billingTime;
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "status")
    private PaymentStatus status;

    @OneToMany(mappedBy = "monthlyInvoice")
    private List<ElectricWaterInvoiceEntity> electricWaterInvoices;

    @OneToMany(mappedBy = "monthlyInvoice")
    private List<VehicleInvoiceEntity> vehicleInvoices;
}
