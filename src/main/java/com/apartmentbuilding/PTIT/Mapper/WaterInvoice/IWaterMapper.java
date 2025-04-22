package com.apartmentbuilding.PTIT.Mapper.WaterInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.WaterInvoice.WaterInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.WaterInvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IWaterMapper {
    WaterInvoiceResponse entityToResponse(WaterInvoiceEntity entity);
    WaterInvoiceEntity requestToEntity(WaterInvoiceRequest request);
}
