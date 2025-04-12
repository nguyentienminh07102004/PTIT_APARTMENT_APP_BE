package com.apartmentbuilding.PTIT.DTO.Request.Apartment;

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
public class ApartmentSearchRequest {
    private Integer page;
    private Integer limit;
}
