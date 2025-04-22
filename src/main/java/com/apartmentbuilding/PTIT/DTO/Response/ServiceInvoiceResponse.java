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
public class ServiceInvoiceResponse {
    private String id;
    private Double unitPrice;
    private Double totalPrice;
    private ApartmentResponse apartmentResponse;
    private ServiceTypeResponse type;
}
