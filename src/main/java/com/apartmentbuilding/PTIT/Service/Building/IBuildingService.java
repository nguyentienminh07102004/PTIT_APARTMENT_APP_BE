package com.apartmentbuilding.PTIT.Service.Building;

import com.apartmentbuilding.PTIT.Domains.BuildingEntity;

public interface IBuildingService {
    BuildingEntity findById(String id);
}
