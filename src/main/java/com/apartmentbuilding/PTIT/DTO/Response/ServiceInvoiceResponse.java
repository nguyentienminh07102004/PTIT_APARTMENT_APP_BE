package com.apartmentbuilding.PTIT.DTO.Response;

import com.apartmentbuilding.PTIT.Common.Enums.PaymentStatus;
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
    private String apartmentName;
    private ServiceTypeResponse type;
    private PaymentStatus status;
}
