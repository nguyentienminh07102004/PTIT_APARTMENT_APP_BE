package com.apartmentbuilding.PTIT.Service.MonthlyInvoice;

import com.apartmentbuilding.PTIT.DTO.Response.MonthlyInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import org.springframework.data.web.PagedModel;

public interface IMonthlyInvoiceService {
    boolean existsById(String id);

    MonthlyInvoiceEntity findById(String id);

    MonthlyInvoiceEntity save(MonthlyInvoiceEntity monthlyInvoice);

    MonthlyInvoiceEntity findByBillingTimeAndApartment_Id(String billingTime, String apartmentId);

    boolean existsByBillingTimeAndApartment_Id(String billingTime, String apartmentId);

    PagedModel<MonthlyInvoiceResponse> findByApartment_Id(String apartmentId, Integer page, Integer limit);
}
