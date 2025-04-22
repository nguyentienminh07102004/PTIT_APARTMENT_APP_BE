package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.ParkingInvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IParkingInvoiceRepository extends JpaRepository<ParkingInvoiceEntity, String> {
    Page<ParkingInvoiceEntity> findByMonthlyInvoice_Apartment_Id(String monthlyInvoiceApartmentId, Pageable pageable);
}
