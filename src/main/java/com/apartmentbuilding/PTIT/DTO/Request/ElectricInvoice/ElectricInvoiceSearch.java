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
public class ElectricInvoiceSearch {
    private String billingTime;
    private String apartmentName;
    private Integer page;
    private Integer limit;
}
