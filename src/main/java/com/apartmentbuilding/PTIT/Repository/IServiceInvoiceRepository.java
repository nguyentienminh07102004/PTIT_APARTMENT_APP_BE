package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.ServiceInvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceInvoiceRepository extends JpaRepository<ServiceInvoiceEntity, String> {
    Page<ServiceInvoiceEntity> findDistinctByMonthlyInvoice_Apartment_User_Email(String monthlyInvoiceApartmentUserEmail, Pageable pageable);
}
