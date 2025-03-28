package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Beans.TypeElectricWater;
import com.apartmentbuilding.PTIT.Domains.ElectricWaterInvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IElectricWaterRepository extends JpaRepository<ElectricWaterInvoiceEntity, String> {
    ElectricWaterInvoiceEntity findDistinctByMonthlyInvoice_Apartment_IdAndMonthlyInvoice_BillingTimeAndType(String monthlyInvoiceApartmentId, String monthlyInvoiceBillingTime, TypeElectricWater type);
    Page<ElectricWaterInvoiceEntity> findDistinctByMonthlyInvoice_Apartment_IdAndType(String monthlyInvoiceApartmentId, TypeElectricWater type, Pageable page);
}