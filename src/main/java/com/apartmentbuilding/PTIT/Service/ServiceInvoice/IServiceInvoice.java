package com.apartmentbuilding.PTIT.Service.ServiceInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.ServiceInvoice.ServiceInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ServiceInvoiceResponse;

public interface IServiceInvoice {
    ServiceInvoiceResponse save(ServiceInvoiceRequest serviceInvoiceRequest);
}
