package com.apartmentbuilding.PTIT.Mapper.Apartment;

import com.apartmentbuilding.PTIT.DTO.Response.ApartmentResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApartmentConvertor {
    private final IApartmentMapper apartmentMapper;

    public ApartmentResponse entityToResponse(ApartmentEntity apartmentEntity) {
        ApartmentResponse apartmentResponse = this.apartmentMapper.entityToResponse(apartmentEntity);
        apartmentResponse.setFloorName(apartmentEntity.getFloor().getFloorName());
        apartmentResponse.setBuildingName(apartmentEntity.getFloor().getBuilding().getName());
        return apartmentResponse;
    }
}
