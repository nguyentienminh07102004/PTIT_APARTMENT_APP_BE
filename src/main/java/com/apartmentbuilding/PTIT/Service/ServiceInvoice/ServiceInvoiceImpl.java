package com.apartmentbuilding.PTIT.Service.ServiceInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.ServiceInvoice.ServiceInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ServiceInvoiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceInvoiceImpl implements IServiceInvoice {
    /**
     * @return
     */
    @Override
    public ServiceInvoiceResponse save(ServiceInvoiceRequest request) {
        return null;
    }
}
