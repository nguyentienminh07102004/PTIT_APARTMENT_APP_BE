package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Beans.TypeElectricWater;
import com.apartmentbuilding.PTIT.Domains.ElectricWaterInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IElectricWaterRepository extends JpaRepository<ElectricWaterInvoiceEntity, String> {
    List<ElectricWaterInvoiceEntity> findDistinctByMonthlyInvoice_Apartment_Id(String monthlyInvoiceApartmentId);
    ElectricWaterInvoiceEntity findDistinctByMonthlyInvoice_Apartment_IdAndMonthlyInvoice_BillingTimeAndType(String monthlyInvoiceApartmentId, String monthlyInvoiceBillingTime, TypeElectricWater type);
}