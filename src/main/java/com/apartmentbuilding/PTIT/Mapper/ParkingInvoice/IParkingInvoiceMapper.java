package com.apartmentbuilding.PTIT.Mapper.ParkingInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.ParkingInvoice.ParkingInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ParkingInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ParkingInvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IParkingInvoiceMapper {
    ParkingInvoiceEntity requestToEntity(ParkingInvoiceRequest request);
    ParkingInvoiceResponse entityToResponse(ParkingInvoiceEntity entity);
}
