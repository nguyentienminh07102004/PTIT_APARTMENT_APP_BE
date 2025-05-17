package com.apartmentbuilding.PTIT.Model.Entity;

import com.apartmentbuilding.PTIT.Common.Enum.PaymentMethod;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "monthlyInvoice")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(value = AuditingEntityListener.class)
public class MonthlyInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column()
    private String id;
    @ManyToOne()
    @JoinColumn(name = "apartmentName", referencedColumnName = "name")
    private ApartmentEntity apartment;
    @Column()
    private Date paymentDate;
    @Column()
    private String billingTime;
    @Column(updatable = false)
    @CreatedDate()
    private Date createdDate;
    @Column()
    private Date dueDate;
    @Column()
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "monthlyInvoice")
    private List<ParkingInvoiceEntity> packingInvoices;

    @OneToOne(mappedBy = "monthlyInvoice")
    private WaterInvoiceEntity waterInvoice;

    @OneToOne(mappedBy = "monthlyInvoice")
    private ElectricInvoiceEntity electricInvoice;

    @OneToMany(mappedBy = "monthlyInvoice")
    @Builder.Default
    private List<ServiceInvoiceEntity> serviceInvoice = new ArrayList<>();
}
