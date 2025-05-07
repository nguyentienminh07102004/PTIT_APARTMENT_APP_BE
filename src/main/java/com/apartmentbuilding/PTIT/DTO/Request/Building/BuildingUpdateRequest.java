package com.apartmentbuilding.PTIT.DTO.Request.Building;

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
public class BuildingUpdateRequest {
    private String id;
    private String name;
    private String address;
    private Date buildAt;
    private String description;
}
