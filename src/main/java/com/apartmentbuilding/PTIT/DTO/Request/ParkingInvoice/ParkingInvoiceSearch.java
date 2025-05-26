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
public class ParkingInvoiceSearch {
    private String search;
    private Integer page;
    private Integer limit;
}
