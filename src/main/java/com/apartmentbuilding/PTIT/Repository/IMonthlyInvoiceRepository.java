package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Domains.MonthlyInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMonthlyInvoiceRepository extends JpaRepository<MonthlyInvoiceEntity, String> {
    MonthlyInvoiceEntity findByBillingTimeAndApartment_Id(String billingTime, String apartmentId);
    boolean existsByBillingTimeAndApartment_Id(String billingTime, String apartmentId);
}
