package com.apartmentbuilding.PTIT.Mapper.WaterInvoice;

import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.WaterInvoiceEntity;
import com.apartmentbuilding.PTIT.Repository.IMonthlyInvoiceRepository;
import com.apartmentbuilding.PTIT.Utils.BillingTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaterConvertor {
    private final IWaterMapper waterMapper;
    private final IMonthlyInvoiceRepository monthlyInvoiceRepository;

    public WaterInvoiceResponse entityToResponse(WaterInvoiceEntity entity) {
        WaterInvoiceResponse response = waterMapper.entityToResponse(entity);
        response.setApartmentId(entity.getMonthlyInvoice().getApartment().getId());
        String beforeBillingTime = BillingTimeUtils.getBillingTimeBefore(entity.getMonthlyInvoice().getBillingTime());
        MonthlyInvoiceEntity beforeMonthlyInvoice = this.monthlyInvoiceRepository.findByBillingTimeAndApartment_Id(beforeBillingTime, entity.getMonthlyInvoice().getApartment().getId());
        response.setBeforeNumber(beforeMonthlyInvoice.getWaterInvoice().getCurrentNumber());
        response.setTotal(response.getCurrentNumber() - response.getBeforeNumber());
        response.setTotalPrice(response.getTotal() * response.getUnitPrice());
        return response;
    }
}
