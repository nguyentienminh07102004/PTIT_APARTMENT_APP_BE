package com.apartmentbuilding.PTIT.Service.Building;

import com.apartmentbuilding.PTIT.Model.Entity.BuildingEntity;

public interface IBuildingService {
    BuildingEntity findById(String id);
}
