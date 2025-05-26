package com.apartmentbuilding.PTIT.Mapper.Vehicle;

import com.apartmentbuilding.PTIT.DTO.Response.Vehicle.VehicleResponse;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IVehicleMapper {
    @Mapping(target = "type", ignore = true)
    VehicleResponse toResponse(VehicleEntity entity);
}
