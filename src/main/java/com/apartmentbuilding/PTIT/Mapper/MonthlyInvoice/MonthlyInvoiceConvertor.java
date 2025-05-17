package com.apartmentbuilding.PTIT.Mapper.MonthlyInvoice;

import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Response.MonthlyInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Response.ServiceInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.ElectricInvoice.ElectricConvertor;
import com.apartmentbuilding.PTIT.Mapper.ParkingInvoice.ParkingInvoiceConvertor;
import com.apartmentbuilding.PTIT.Mapper.ServiceInvoice.ServiceInvoiceConverter;
import com.apartmentbuilding.PTIT.Mapper.WaterInvoice.WaterConvertor;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.WaterInvoiceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MonthlyInvoiceConvertor {
    private final IMonthlyInvoiceMapper monthlyInvoiceMapper;
    private final ElectricConvertor electricConvertor;
    private final WaterConvertor waterConvertor;
    private final ParkingInvoiceConvertor parkingInvoiceConvertor;
    private final ServiceInvoiceConverter serviceInvoiceConverter;

    public MonthlyInvoiceResponse entityToResponse(MonthlyInvoiceEntity monthlyInvoiceEntity) {
        MonthlyInvoiceResponse monthlyInvoiceResponse = this.monthlyInvoiceMapper.entityToResponse(monthlyInvoiceEntity);
        Double totalPrice = 0.0D;
        monthlyInvoiceResponse.setApartmentName(monthlyInvoiceEntity.getApartment().getName());
        ElectricInvoiceEntity electricInvoice = monthlyInvoiceEntity.getElectricInvoice();
        ElectricInvoiceResponse electricInvoiceResponse = this.electricConvertor.entityToResponse(electricInvoice);
        totalPrice += electricInvoiceResponse.getTotalPrice();
        monthlyInvoiceResponse.setElectricInvoice(electricInvoiceResponse);
        WaterInvoiceEntity waterInvoice = monthlyInvoiceEntity.getWaterInvoice();
        WaterInvoiceResponse waterInvoiceResponse = this.waterConvertor.entityToResponse(waterInvoice);
        totalPrice += waterInvoiceResponse.getTotalPrice();
        monthlyInvoiceResponse.setWaterInvoice(waterInvoiceResponse);
        monthlyInvoiceResponse.setVehicleInvoices(
                monthlyInvoiceEntity.getPackingInvoices().stream()
                        .map(parkingInvoiceConvertor::entityToResponse)
                        .toList()
        );
        totalPrice += monthlyInvoiceEntity.getPackingInvoices()
                .stream()
                .reduce(0.0,  (total, invoice) -> total + invoice.getUnitPrice(), Double::sum);
        List<ServiceInvoiceResponse> serviceInvoices = monthlyInvoiceEntity.getServiceInvoice()
                .stream()
                .map(this.serviceInvoiceConverter::toResponse)
                .toList();
        monthlyInvoiceResponse.setServiceInvoices(serviceInvoices);
        totalPrice += monthlyInvoiceEntity.getServiceInvoice()
                .stream()
                .reduce(0.0,  (total, invoice) -> total + invoice.getUnitPrice(), Double::sum);
        monthlyInvoiceResponse.setTotalPrice(totalPrice);
        return monthlyInvoiceResponse;
    }
}