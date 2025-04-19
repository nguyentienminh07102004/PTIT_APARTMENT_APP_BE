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
@Table(name = "electricInvoices")
@Getter
@Setter
public class ElectricInvoiceEntity {
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
