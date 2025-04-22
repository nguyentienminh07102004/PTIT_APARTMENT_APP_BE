package com.apartmentbuilding.PTIT.Mapper.Apartment;

import com.apartmentbuilding.PTIT.DTO.Response.ApartmentResponse;
import com.apartmentbuilding.PTIT.Mapper.Building.IBuildingMapper;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApartmentConvertor {
    private final IApartmentMapper apartmentMapper;
    private final IBuildingMapper buildingMapper;

    public ApartmentResponse entityToResponse(ApartmentEntity apartmentEntity) {
        ApartmentResponse apartmentResponse = this.apartmentMapper.entityToResponse(apartmentEntity);
//        BuildingResponse buildingResponse = this.buildingMapper.entityToResponse(apartmentEntity.getBuilding());
//        apartmentResponse.setBuilding(buildingResponse);
        return apartmentResponse;
    }
}
