package com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectricInvoiceRequest {
    private Integer currentNumber;
    private Double unitPrice;
    private String billingTime;
    private String apartmentId;
}
