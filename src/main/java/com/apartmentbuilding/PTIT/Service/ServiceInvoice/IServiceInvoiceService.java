package com.apartmentbuilding.PTIT.Service.ServiceInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.ServiceInvoice.ServiceInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ServiceInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ServiceInvoiceEntity;
import org.springframework.data.web.PagedModel;

public interface IServiceInvoiceService {
    ServiceInvoiceResponse save(ServiceInvoiceRequest serviceInvoiceRequest);

    ServiceInvoiceEntity findById(String id);

    PagedModel<ServiceInvoiceResponse> findMyInvoices(Integer page, Integer limit);

    PagedModel<ServiceInvoiceResponse> findAllInvoices(Integer page, Integer limit);
}
