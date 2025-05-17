package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.ElectricInvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IElectricRepository extends JpaRepository<ElectricInvoiceEntity, String>, JpaSpecificationExecutor<ElectricInvoiceEntity> {
    @Query(value = """
            select sum(e.currentNumber * e.unitPrice) from electricinvoices e 
                inner join monthlyinvoice m on m.id = e.monthlyinvoiceid
                where m.billingtime = :billingTime
                        group by m.billingTime
            """, nativeQuery = true)
    Double findTotalPriceByMonthlyInvoice_BillingTime(@Param(value = "billingTime") String billingTime);
}