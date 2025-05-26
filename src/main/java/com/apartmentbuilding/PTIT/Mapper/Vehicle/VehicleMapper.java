package com.apartmentbuilding.PTIT.Mapper.Vehicle;

import com.apartmentbuilding.PTIT.DTO.Response.Vehicle.VehicleResponse;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleMapper {
    private final IVehicleMapper vehicleMapper;

    public VehicleResponse toResponse(VehicleEntity vehicle) {
        VehicleResponse response = this.vehicleMapper.toResponse(vehicle);
        response.setType(vehicle.getType().getName());
        response.setApartmentName(vehicle.getApartment().getName());
        return response;
    }
}
