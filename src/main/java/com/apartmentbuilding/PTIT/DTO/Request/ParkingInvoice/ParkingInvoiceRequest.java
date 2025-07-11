package com.apartmentbuilding.PTIT.DTO.Request.ParkingInvoice;

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
public class ParkingInvoiceRequest {
    private String apartmentName;
    private Double unitPrice;
    private String billingTime;
    private String licensePlate;
}
