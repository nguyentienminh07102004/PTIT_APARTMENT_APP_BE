package com.apartmentbuilding.PTIT.Mapper.ParkingInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.VehicleInvoice.VehicleInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.VehicleInvoiceResponse;
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

    public ParkingInvoiceEntity requestToEntity(VehicleInvoiceRequest request, MonthlyInvoiceEntity monthlyInvoice) {
        ParkingInvoiceEntity entity = this.vehicleInvoiceMapper.requestToEntity(request);
        entity.setMonthlyInvoice(monthlyInvoice);
        entity.setVehicle(this.vehicleService.findByLicensePlate(request.getLicensePlate()));
        return entity;
    }

    public VehicleInvoiceResponse entityToResponse(ParkingInvoiceEntity entity) {
        VehicleInvoiceResponse response = this.vehicleInvoiceMapper.entityToResponse(entity);
        response.setApartmentId(entity.getMonthlyInvoice().getApartment().getId());
        response.setBillingTime(entity.getMonthlyInvoice().getBillingTime());
        response.setLicensePlate(entity.getVehicle().getLicensePlate());
        return response;
    }

}
