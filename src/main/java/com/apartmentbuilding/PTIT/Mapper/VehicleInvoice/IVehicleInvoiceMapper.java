package com.apartmentbuilding.PTIT.Mapper.VehicleInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.VehicleInvoice.ParkingInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.VehicleInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleInvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IVehicleInvoiceMapper {
    VehicleInvoiceEntity requestToEntity(ParkingInvoiceRequest request);
    VehicleInvoiceResponse entityToResponse(VehicleInvoiceEntity entity);
}
