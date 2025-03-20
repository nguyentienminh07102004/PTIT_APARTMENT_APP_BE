package com.apartmentbuilding.PTIT.DTO.Request.ElectrictWater;

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
public class ElectricRequest {
    private Integer currentNumber;
    private Double unitPrice;
    private String billingTime;
    private String apartmentId;
}
