package com.apartmentbuilding.PTIT.DTO.Response;

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
public class VehicleInvoiceResponse {
    private String id;
    private String licensePlate;
    private Double unitPrice;
    private String apartmentId;
    private String billingTime;
}
