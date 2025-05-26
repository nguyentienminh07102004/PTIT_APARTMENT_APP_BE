package com.apartmentbuilding.PTIT.Mapper.ParkingInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.ParkingInvoice.ParkingInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ParkingInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.ParkingInvoiceEntity;
import com.apartmentbuilding.PTIT.Service.Vehicle.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParkingInvoiceConvertor {
    private final IParkingInvoiceMapper vehicleInvoiceMapper;
    private final IVehicleService vehicleService;

    public ParkingInvoiceEntity requestToEntity(ParkingInvoiceRequest request, MonthlyInvoiceEntity monthlyInvoice) {
        ParkingInvoiceEntity entity = this.vehicleInvoiceMapper.requestToEntity(request);
        entity.setMonthlyInvoice(monthlyInvoice);
        entity.setVehicle(this.vehicleService.findByLicensePlate(request.getLicensePlate()));
        return entity;
    }

    public ParkingInvoiceResponse entityToResponse(ParkingInvoiceEntity entity) {
        ParkingInvoiceResponse response = this.vehicleInvoiceMapper.entityToResponse(entity);
        response.setApartmentName(entity.getMonthlyInvoice().getApartment().getName());
        response.setBillingTime(entity.getMonthlyInvoice().getBillingTime());
        response.setLicensePlate(entity.getVehicle().getLicensePlate());
        response.setType(entity.getVehicle().getType().getName());
        response.setStatus(entity.getMonthlyInvoice().getStatus());
        return response;
    }

}
