package com.apartmentbuilding.PTIT.Mapper.ParkingInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.VehicleInvoice.ParkingInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.VehicleInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ParkingInvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IParkingInvoiceMapper {
    ParkingInvoiceEntity requestToEntity(ParkingInvoiceRequest request);
    VehicleInvoiceResponse entityToResponse(ParkingInvoiceEntity entity);
}
