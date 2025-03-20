package com.apartmentbuilding.PTIT.Service.MonthlyInvoice;

import com.apartmentbuilding.PTIT.Domains.MonthlyInvoiceEntity;

public interface IMonthlyInvoiceService {
    boolean existsById(String id);

    MonthlyInvoiceEntity findById(String id);

    MonthlyInvoiceEntity save(MonthlyInvoiceEntity monthlyInvoice);

    MonthlyInvoiceEntity findByBillingTimeAndApartment_Id(String billingTime, String apartmentId);

    boolean existsByBillingTimeAndApartment_Id(String billingTime, String apartmentId);
}
