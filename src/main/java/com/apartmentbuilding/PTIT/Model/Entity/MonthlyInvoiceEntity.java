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
    @Column()
    private String id;
    @ManyToOne()
    @JoinColumn()
    private ApartmentEntity apartment;
    @Column()
    private Date paidDate;
    @Column()
    private String billingTime;
    @Column()
    private Date createdDate;
    @Column()
    private PaymentStatus status;
    @Column()
    private Date dueDate;

    @OneToMany(mappedBy = "monthlyInvoice")
    private List<VehicleInvoiceEntity> vehicleInvoices;
}
