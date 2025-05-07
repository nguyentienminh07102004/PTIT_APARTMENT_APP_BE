package com.apartmentbuilding.PTIT.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "waterInvoices")
@Getter
@Setter
public class WaterInvoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column()
    private String id;
    @Column()
    private Double unitPrice;
    @Column()
    private Integer currentNumber;

    @OneToOne
    @JoinColumn(name = "monthlyInvoiceId")
    private MonthlyInvoiceEntity monthlyInvoice;
}
