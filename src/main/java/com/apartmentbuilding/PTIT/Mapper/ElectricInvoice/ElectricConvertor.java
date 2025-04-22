package com.apartmentbuilding.PTIT.Mapper.ElectricInvoice;

import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Repository.IMonthlyInvoiceRepository;
import com.apartmentbuilding.PTIT.Utils.BillingTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElectricConvertor {
    private final IElectricMapper electricMapper;
    private final IMonthlyInvoiceRepository monthlyInvoiceRepository;

    public ElectricInvoiceResponse entityToResponse(ElectricInvoiceEntity entity) {
        ElectricInvoiceResponse response = electricMapper.entityToResponse(entity);
        response.setApartmentId(entity.getMonthlyInvoice().getApartment().getId());
        String beforeBillingTime = BillingTimeUtils.getBillingTimeBefore(entity.getMonthlyInvoice().getBillingTime());
        MonthlyInvoiceEntity beforeMonthlyInvoice = this.monthlyInvoiceRepository.findByBillingTimeAndApartment_Id(beforeBillingTime, entity.getMonthlyInvoice().getApartment().getId());
        response.setBeforeNumber(beforeMonthlyInvoice.getElectricInvoice().getCurrentNumber());
        response.setTotal(response.getCurrentNumber() - response.getBeforeNumber());
        response.setTotalPrice(response.getTotal() * response.getUnitPrice());
        return response;
    }
}
