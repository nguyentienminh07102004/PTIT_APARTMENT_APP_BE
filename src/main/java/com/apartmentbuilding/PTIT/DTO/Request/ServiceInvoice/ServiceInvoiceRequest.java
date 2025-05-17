package com.apartmentbuilding.PTIT.DTO.Request.ServiceInvoice;

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
public class ServiceInvoiceRequest {
    private String billingTime;
    private String typeName;
    private String apartmentName;
}
