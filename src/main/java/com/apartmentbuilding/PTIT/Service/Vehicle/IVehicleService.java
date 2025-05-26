package com.apartmentbuilding.PTIT.Service.Vehicle;

import com.apartmentbuilding.PTIT.DTO.Request.Vehicle.VehicleCreate;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleEntity;

import java.util.List;

public interface IVehicleService {
    VehicleEntity findById(String id);
    VehicleEntity findByLicensePlate(String licensePlate);

    VehicleEntity create(VehicleCreate create);
    void delete(List<String> ids);
}
