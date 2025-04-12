package com.apartmentbuilding.PTIT.Mapper.VehicleInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.VehicleInvoice.VehicleInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.VehicleInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleInvoiceEntity;
import com.apartmentbuilding.PTIT.Service.Vehicle.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleInvoiceConvertor {
    private final IVehicleInvoiceMapper vehicleInvoiceMapper;
    private final IVehicleService vehicleService;

    public VehicleInvoiceEntity requestToEntity(VehicleInvoiceRequest request, MonthlyInvoiceEntity monthlyInvoice) {
        VehicleInvoiceEntity entity = this.vehicleInvoiceMapper.requestToEntity(request);
        entity.setMonthlyInvoice(monthlyInvoice);
        entity.setVehicle(this.vehicleService.findByLicensePlate(request.getLicensePlate()));
        return entity;
    }

    public VehicleInvoiceResponse entityToResponse(VehicleInvoiceEntity entity) {
        VehicleInvoiceResponse response = this.vehicleInvoiceMapper.entityToResponse(entity);
        response.setApartmentId(entity.getMonthlyInvoice().getApartment().getId());
        response.setBillingTime(entity.getMonthlyInvoice().getBillingTime());
        response.setLicensePlate(entity.getVehicle().getLicensePlate());
        return response;
    }

}
