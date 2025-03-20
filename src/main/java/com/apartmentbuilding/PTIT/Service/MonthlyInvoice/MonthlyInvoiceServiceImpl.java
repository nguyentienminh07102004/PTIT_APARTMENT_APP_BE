package com.apartmentbuilding.PTIT.Service.MonthlyInvoice;

import com.apartmentbuilding.PTIT.Domains.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.ExceptionAdvice.ExceptionVariable;
import com.apartmentbuilding.PTIT.Repository.IMonthlyInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MonthlyInvoiceServiceImpl implements IMonthlyInvoiceService {
    private final IMonthlyInvoiceRepository monthlyInvoiceRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String id) {
        return monthlyInvoiceRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MonthlyInvoiceEntity findById(String id) {
        return monthlyInvoiceRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.MONTHLY_INVOICE_NOT_FOUND));
    }

    @Override
    @Transactional
    public MonthlyInvoiceEntity save(MonthlyInvoiceEntity monthlyInvoice) {
        return monthlyInvoiceRepository.save(monthlyInvoice);
    }

    @Override
    @Transactional(readOnly = true)
    public MonthlyInvoiceEntity findByBillingTimeAndApartment_Id(String billingTime, String apartmentId) {
        return monthlyInvoiceRepository.findByBillingTimeAndApartment_Id(billingTime, apartmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByBillingTimeAndApartment_Id(String billingTime, String apartmentId) {
        return monthlyInvoiceRepository.existsByBillingTimeAndApartment_Id(billingTime, apartmentId);
    }
}
