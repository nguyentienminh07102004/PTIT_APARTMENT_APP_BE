package com.apartmentbuilding.PTIT.DTO.Request.MonthInvoice;

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
public class MonthInvoiceSearch {
    private Integer page;
    private Integer limit;
    private String search;
}
