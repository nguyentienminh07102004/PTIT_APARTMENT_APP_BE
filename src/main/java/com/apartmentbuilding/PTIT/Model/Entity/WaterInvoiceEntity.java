package com.apartmentbuilding.PTIT.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "waterInvoices")
@Getter
@Setter
public class WaterInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;
    @Column(name = "unitPrice")
    private Double unitPrice;
    @Column(name = "currentNumber")
    private Integer currentNumber;

    @ManyToOne
    @JoinColumn(name = "monthlyInvoiceId")
    private MonthlyInvoiceEntity monthlyInvoice;
}
