package com.apartmentbuilding.PTIT.Mapper.Apartment;

import com.apartmentbuilding.PTIT.DTO.Reponse.ApartmentResponse;
import com.apartmentbuilding.PTIT.DTO.Request.Apartment.ApartmentRequest;
import com.apartmentbuilding.PTIT.Domains.ApartmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IApartmentMapper {

    ApartmentEntity requestToEntity(ApartmentRequest request);
    ApartmentResponse entityToResponse(ApartmentEntity entity);
}
