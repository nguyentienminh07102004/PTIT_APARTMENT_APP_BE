package com.apartmentbuilding.PTIT.Service.VehicleType;

import com.apartmentbuilding.PTIT.Model.Entity.VehicleTypeEntity;

public interface IVehicleTypeService {
    VehicleTypeEntity findByName(String id);
}
