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

@Entity
@Table(name = "serviceInvoices")
@Getter
@Setter
public class ServiceInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column()
    private String id;
    @Column()
    private Double unitPrice;
    @ManyToOne
    @JoinColumn(name = "serviceName", referencedColumnName = "name")
    private ServiceTypeEntity type;

    @ManyToOne
    @JoinColumn(name = "monthInvoiceId")
    private MonthlyInvoiceEntity monthlyInvoice;
}
