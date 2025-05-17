package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMonthlyInvoiceRepository extends JpaRepository<MonthlyInvoiceEntity, String> {
    MonthlyInvoiceEntity findByBillingTimeAndApartment_Name(String billingTime, String apartmentId);
    boolean existsByBillingTimeAndApartment_Id(String billingTime, String apartmentId);
    Page<MonthlyInvoiceEntity> findByApartment_Name(String apartmentName, Pageable pagination);

    Page<MonthlyInvoiceEntity> findDistinctByApartment_User_Email(String apartmentUserEmail, Pageable pagination);
}
