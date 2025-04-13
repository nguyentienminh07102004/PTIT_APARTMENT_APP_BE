package com.apartmentbuilding.PTIT.Mapper.MonthlyInvoice;

import com.apartmentbuilding.PTIT.Common.Enum.TypeElectricWater;
import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Response.MonthlyInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricWaterInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Mapper.Electric.ElectricConvertor;
import com.apartmentbuilding.PTIT.Mapper.VehicleInvoice.VehicleInvoiceConvertor;
import com.apartmentbuilding.PTIT.Mapper.Water.WaterConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MonthlyInvoiceConvertor {
    private final IMonthlyInvoiceMapper monthlyInvoiceMapper;
    private final ElectricConvertor electricConvertor;
    private final WaterConvertor waterConvertor;
    private final VehicleInvoiceConvertor vehicleInvoiceConvertor;

    public MonthlyInvoiceResponse entityToResponse(MonthlyInvoiceEntity monthlyInvoiceEntity) {
        MonthlyInvoiceResponse monthlyInvoiceResponse = monthlyInvoiceMapper.entityToResponse(monthlyInvoiceEntity);
        List<ElectricWaterInvoiceEntity> electricWaterInvoices = monthlyInvoiceEntity.getElectricWaterInvoices();
        ElectricWaterInvoiceEntity electricInvoice;
        ElectricWaterInvoiceEntity waterInvoice;
        Double totalPrice = monthlyInvoiceEntity.getApartment().getArea() * monthlyInvoiceEntity.getApartment().getBuilding().getUnitNumber();
        if (electricWaterInvoices.get(0).getType().equals(TypeElectricWater.ELECTRIC)) {
            electricInvoice = electricWaterInvoices.get(0);
            waterInvoice = electricWaterInvoices.get(1);
        } else {
            waterInvoice = electricWaterInvoices.get(0);
            electricInvoice = electricWaterInvoices.get(1);
        }
        ElectricInvoiceResponse electricInvoiceResponse = electricConvertor.entityToResponse(electricInvoice);
        WaterInvoiceResponse waterInvoiceResponse = waterConvertor.entityToResponse(waterInvoice);
        totalPrice += electricInvoiceResponse.getTotalPrice();
        totalPrice += waterInvoiceResponse.getTotalPrice();
        monthlyInvoiceResponse.setElectricInvoice(electricInvoiceResponse);
        monthlyInvoiceResponse.setWaterInvoice(waterInvoiceResponse);
        monthlyInvoiceResponse.setVehicleInvoices(
                monthlyInvoiceEntity.getVehicleInvoices().stream()
                        .map(vehicleInvoiceConvertor::entityToResponse)
                        .toList()
        );
        totalPrice += monthlyInvoiceEntity.getVehicleInvoices()
                .stream()
                .reduce(0.0,  (total, invoice) -> total + invoice.getUnitPrice(), Double::sum);
        monthlyInvoiceResponse.setTotalPrice(totalPrice);
        return monthlyInvoiceResponse;
    }
}