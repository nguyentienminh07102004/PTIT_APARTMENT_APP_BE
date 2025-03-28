package com.apartmentbuilding.PTIT.Mapper.Water;

import com.apartmentbuilding.PTIT.DTO.Reponse.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Request.ElectrictWater.WaterRequest;
import com.apartmentbuilding.PTIT.Domains.ElectricWaterInvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WaterMapper {
    WaterInvoiceResponse entityToResponse(ElectricWaterInvoiceEntity entity);
    ElectricWaterInvoiceEntity requestToEntity(WaterRequest request);
}
