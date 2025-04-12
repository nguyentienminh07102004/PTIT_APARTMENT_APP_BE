package com.apartmentbuilding.PTIT.Mapper.Building;

import com.apartmentbuilding.PTIT.DTO.Response.BuildingResponse;
import com.apartmentbuilding.PTIT.Model.Entity.BuildingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IBuildingMapper {
    BuildingResponse entityToResponse(BuildingEntity building);
}
