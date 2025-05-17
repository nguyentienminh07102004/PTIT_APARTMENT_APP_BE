package com.apartmentbuilding.PTIT.Mapper.Amenity;

import com.apartmentbuilding.PTIT.DTO.Response.Amenity.AmenityResponse;
import com.apartmentbuilding.PTIT.Model.Entity.AmenityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IAmenityMapper {
    AmenityResponse toResponse(AmenityEntity amenityEntity);
}
