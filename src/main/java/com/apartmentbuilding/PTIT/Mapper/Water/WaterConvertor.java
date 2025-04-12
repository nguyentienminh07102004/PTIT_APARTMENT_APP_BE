package com.apartmentbuilding.PTIT.Mapper.Water;

import com.apartmentbuilding.PTIT.Common.Enum.TypeElectricWater;
import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricWaterInvoiceEntity;
import com.apartmentbuilding.PTIT.Repository.IElectricWaterRepository;
import com.apartmentbuilding.PTIT.Utils.BillingTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaterConvertor {
    private final WaterMapper waterMapper;
    private final IElectricWaterRepository electricWaterRepository;

    public WaterInvoiceResponse entityToResponse(ElectricWaterInvoiceEntity entity) {
        WaterInvoiceResponse response = waterMapper.entityToResponse(entity);
        response.setApartmentId(entity.getMonthlyInvoice().getApartment().getId());
        String beforeBillingTime = BillingTimeUtils.getBillingTimeBefore(entity.getMonthlyInvoice().getBillingTime());
        ElectricWaterInvoiceEntity electricWaterInvoiceEntity =
                electricWaterRepository.findDistinctByMonthlyInvoice_Apartment_IdAndMonthlyInvoice_BillingTimeAndType(entity.getMonthlyInvoice().getApartment().getId(),
                        beforeBillingTime, TypeElectricWater.WATER);
        response.setBeforeNumber(electricWaterInvoiceEntity.getCurrentNumber());
        response.setTotal(response.getCurrentNumber() - electricWaterInvoiceEntity.getCurrentNumber());
        response.setTotalPrice(response.getTotal() * response.getUnitPrice());
        return response;
    }
}
