package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.VehicleInvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehicleInvoiceRepository extends JpaRepository<VehicleInvoiceEntity, String> {
    Page<VehicleInvoiceEntity> findByMonthlyInvoice_Apartment_Id(String monthlyInvoiceApartmentId, Pageable pageable);
}
