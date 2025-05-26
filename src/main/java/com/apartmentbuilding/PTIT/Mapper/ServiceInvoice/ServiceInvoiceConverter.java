package com.apartmentbuilding.PTIT.Mapper.ServiceInvoice;

import com.apartmentbuilding.PTIT.DTO.Response.ServiceInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ServiceInvoiceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceInvoiceConverter {
    private final IServiceInvoiceMapper serviceInvoiceMapper;

    public ServiceInvoiceResponse toResponse(ServiceInvoiceEntity serviceInvoiceEntity) {
        ServiceInvoiceResponse serviceInvoiceResponse = this.serviceInvoiceMapper.toResponse(serviceInvoiceEntity);
        serviceInvoiceResponse.setApartmentName(serviceInvoiceEntity.getMonthlyInvoice().getApartment().getName());
        serviceInvoiceResponse.setStatus(serviceInvoiceEntity.getMonthlyInvoice().getStatus());
        return serviceInvoiceResponse;
    }
}
