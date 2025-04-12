package com.apartmentbuilding.PTIT.DTO.Request.VehicleInvoice;

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
public class VehicleInvoiceRequest {
    private String apartmentId;
    private String typeName;
    private Double unitPrice;
    private String billingTime;
    private Integer quantity;
}
