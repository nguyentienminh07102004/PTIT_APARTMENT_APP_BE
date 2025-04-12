package com.apartmentbuilding.PTIT.Service.Vehicle;

import com.apartmentbuilding.PTIT.Model.Entity.VehicleEntity;

public interface IVehicleService {
    VehicleEntity findById(String id);
    VehicleEntity findByLicensePlate(String licensePlate);
}
