package com.apartmentbuilding.PTIT.Mapper.VehicleInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.VehicleInvoice.VehicleInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.VehicleInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleTypeEntity;
import com.apartmentbuilding.PTIT.Service.VehicleType.IVehicleTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleInvoiceConvertor {
    private final IVehicleInvoiceMapper vehicleInvoiceMapper;
    private final IVehicleTypeService vehicleTypeService;

    public VehicleInvoiceEntity requestToEntity(VehicleInvoiceRequest request, MonthlyInvoiceEntity monthlyInvoice) {
        VehicleInvoiceEntity entity = vehicleInvoiceMapper.requestToEntity(request);
        entity.setMonthlyInvoice(monthlyInvoice);
        VehicleTypeEntity type = vehicleTypeService.findByName(request.getTypeName());
        entity.setType(type);
        return entity;
    }

    public VehicleInvoiceResponse entityToResponse(VehicleInvoiceEntity entity) {
        VehicleInvoiceResponse response = vehicleInvoiceMapper.entityToResponse(entity);
        response.setApartmentId(entity.getMonthlyInvoice().getApartment().getId());
        response.setBillingTime(entity.getMonthlyInvoice().getBillingTime());
        response.setType(entity.getType().getName());
        response.setTotalPrice(entity.getUnitPrice() * entity.getQuantity());
        return response;
    }

}
