package com.apartmentbuilding.PTIT.Mapper.Electric;

import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Request.ElectrictWater.ElectricRequest;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricWaterInvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ElectricMapper {
    ElectricWaterInvoiceEntity requestToEntity(ElectricRequest request);
    ElectricInvoiceResponse entityToResponse(ElectricWaterInvoiceEntity entity);
}
