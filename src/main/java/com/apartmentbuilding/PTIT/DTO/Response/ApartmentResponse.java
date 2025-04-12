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
public class ApartmentResponse {
    private String id;
    private String name;
    private String unitNumber;
    private String description;
    private Double area;
    private Integer floor;
    private BuildingResponse building;

}
