package com.apartmentbuilding.PTIT.Mapper.Electric;

import com.apartmentbuilding.PTIT.Beans.TypeElectricWater;
import com.apartmentbuilding.PTIT.DTO.Reponse.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.Domains.ElectricWaterInvoiceEntity;
import com.apartmentbuilding.PTIT.Repository.IElectricWaterRepository;
import com.apartmentbuilding.PTIT.Utils.BillingTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ElectricConvertor {
    private final ElectricMapper electricMapper;
    private final IElectricWaterRepository electricWaterRepository;

    public ElectricInvoiceResponse entityToResponse(ElectricWaterInvoiceEntity entity) {
        ElectricInvoiceResponse response = electricMapper.entityToResponse(entity);
        response.setApartmentId(entity.getMonthlyInvoice().getApartment().getId());
        String beforeBillingTime = BillingTimeUtils.getBillingTimeBefore(entity.getMonthlyInvoice().getBillingTime());
        ElectricWaterInvoiceEntity electricWaterInvoiceEntity =
                electricWaterRepository.findDistinctByMonthlyInvoice_Apartment_IdAndMonthlyInvoice_BillingTimeAndType(entity.getMonthlyInvoice().getApartment().getId(),
                        beforeBillingTime, TypeElectricWater.ELECTRIC);
        response.setBeforeNumber(electricWaterInvoiceEntity.getCurrentNumber());
        response.setTotal(response.getCurrentNumber() - electricWaterInvoiceEntity.getCurrentNumber());
        response.setTotalPrice(response.getTotal() * response.getUnitPrice());
        return response;
    }
}
