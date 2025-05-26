package com.apartmentbuilding.PTIT.DTO.Response.Vehicle;

import com.apartmentbuilding.PTIT.Common.Enums.VehicleStatus;
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
public class VehicleResponse {
    private String id;
    private String apartmentName;
    private String licensePlate;
    private VehicleStatus status;
    private String type;
}
