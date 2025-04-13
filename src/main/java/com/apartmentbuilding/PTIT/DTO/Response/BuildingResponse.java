package com.apartmentbuilding.PTIT.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingResponse {
    private String id;
    private String name;
    private String address;
    private Date buildAt;
    private String description;
    private Double unitNumber;
}
