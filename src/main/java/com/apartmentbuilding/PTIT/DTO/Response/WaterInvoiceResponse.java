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
public class WaterInvoiceResponse {
    private String id;
    private String apartmentName;
    private String billingTime;
    private Integer beforeNumber;
    private Integer currentNumber;
    private Integer total;
    private Double unitPrice;
    private Double totalPrice;
    private PaymentStatus status;
}
