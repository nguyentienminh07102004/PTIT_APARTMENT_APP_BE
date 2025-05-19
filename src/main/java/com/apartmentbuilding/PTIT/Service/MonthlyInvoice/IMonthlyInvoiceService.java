package com.apartmentbuilding.PTIT.Service.MonthlyInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.MonthInvoice.MonthInvoiceSearch;
import com.apartmentbuilding.PTIT.DTO.Response.MonthlyInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import org.springframework.data.web.PagedModel;

public interface IMonthlyInvoiceService {
    boolean existsById(String id);

    MonthlyInvoiceEntity findById(String id);

    MonthlyInvoiceResponse findResponseById(String id);

    MonthlyInvoiceEntity save(MonthlyInvoiceEntity monthlyInvoice);

    MonthlyInvoiceEntity findByBillingTimeAndApartment_Name(String billingTime, String apartmentName);

    boolean existsByBillingTimeAndApartment_Id(String billingTime, String apartmentId);

    PagedModel<MonthlyInvoiceResponse> findByApartment_Name(String apartmentName, Integer page, Integer limit);

    PagedModel<MonthlyInvoiceResponse> findMyInvoice(MonthInvoiceSearch search);
}
