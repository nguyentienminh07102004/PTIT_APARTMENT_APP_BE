package com.apartmentbuilding.PTIT.Service.Building;

import com.apartmentbuilding.PTIT.DTO.Request.Building.BuildingSearchRequest;
import com.apartmentbuilding.PTIT.DTO.Response.BuildingResponse;
import com.apartmentbuilding.PTIT.Model.Entity.BuildingEntity;
import org.springframework.data.web.PagedModel;

public interface IBuildingService {
    BuildingEntity findById(String id);
    BuildingEntity save(BuildingEntity building);
    boolean existsByName(String name);
    boolean isEmpty();
    PagedModel<BuildingResponse> findAll(BuildingSearchRequest buildingSearchRequest);
}
