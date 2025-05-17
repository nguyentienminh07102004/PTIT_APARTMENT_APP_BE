package com.apartmentbuilding.PTIT.DTO.Request.Vehicle;

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
public class VehicleCreate {
    private String licensePlate;
    private String apartmentName;
    private String vehicleType;
}
