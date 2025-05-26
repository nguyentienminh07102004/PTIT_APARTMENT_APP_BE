package com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice;

import com.apartmentbuilding.PTIT.Common.Enums.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ElectricInvoiceUpdate {
    @NotNull(message = "ELECTRIC_INVOICE_NOT_FOUND")
    @NotBlank(message = "ELECTRIC_INVOICE_NOT_FOUND")
    private String id;
    @NotNull(message = "INVOICE_CURRENT_NOT_NULL")
    private Integer currentNumber;
    @NotNull(message = "INVOICE_UNIT_PRICE_NOT_NULL")
    private Double unitPrice;
    @NotNull(message = "BILLING_TIME_NOT_NULL_OR_BLANK")
    @NotBlank(message = "BILLING_TIME_NOT_NULL_OR_BLANK")
    private String billingTime;
    @NotNull(message = "APARTMENT_NOT_FOUND")
    @NotBlank(message = "APARTMENT_NOT_FOUND")
    private String apartmentName;
    @NotNull(message = "STATUS_NOT_NULL")
    private PaymentStatus status;
}
