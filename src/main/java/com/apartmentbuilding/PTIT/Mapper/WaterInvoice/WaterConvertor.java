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
        response.setApartmentName(entity.getMonthlyInvoice().getApartment().getName());
        String beforeBillingTime = BillingTimeUtils.getBillingTimeBefore(entity.getMonthlyInvoice().getBillingTime());
        MonthlyInvoiceEntity beforeMonthlyInvoice = this.monthlyInvoiceRepository.findByBillingTimeAndApartment_Name(beforeBillingTime, entity.getMonthlyInvoice().getApartment().getName());
        if (beforeMonthlyInvoice != null) {
            response.setBeforeNumber(beforeMonthlyInvoice.getWaterInvoice().getCurrentNumber());
        } else response.setBeforeNumber(0);
        response.setTotal(response.getCurrentNumber() - response.getBeforeNumber());
        response.setBillingTime(entity.getMonthlyInvoice().getBillingTime());
        response.setTotalPrice(response.getTotal() * response.getUnitPrice());
        response.setStatus(entity.getMonthlyInvoice().getStatus());
        return response;
    }
}
