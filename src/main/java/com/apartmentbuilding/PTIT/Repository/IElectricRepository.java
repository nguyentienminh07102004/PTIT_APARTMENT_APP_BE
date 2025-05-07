package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.ElectricInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IElectricRepository extends JpaRepository<ElectricInvoiceEntity, String>, JpaSpecificationExecutor<ElectricInvoiceEntity> {

}