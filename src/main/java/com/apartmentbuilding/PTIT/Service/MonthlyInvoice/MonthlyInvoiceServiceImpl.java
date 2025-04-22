package com.apartmentbuilding.PTIT.Service.MonthlyInvoice;

import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Response.MonthlyInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.MonthlyInvoice.MonthlyInvoiceConvertor;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Repository.IMonthlyInvoiceRepository;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MonthlyInvoiceServiceImpl implements IMonthlyInvoiceService {
    private final IMonthlyInvoiceRepository monthlyInvoiceRepository;
    private final MonthlyInvoiceConvertor monthlyInvoiceConvertor;

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String id) {
        return this.monthlyInvoiceRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public MonthlyInvoiceEntity findById(String id) {
        return this.monthlyInvoiceRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.MONTHLY_INVOICE_NOT_FOUND));
    }

    @Override
    @Transactional
    public MonthlyInvoiceEntity save(MonthlyInvoiceEntity monthlyInvoice) {
        return this.monthlyInvoiceRepository.save(monthlyInvoice);
    }

    @Override
    @Transactional(readOnly = true)
    public MonthlyInvoiceEntity findByBillingTimeAndApartment_Id(String billingTime, String apartmentId) {
        return this.monthlyInvoiceRepository.findByBillingTimeAndApartment_Id(billingTime, apartmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByBillingTimeAndApartment_Id(String billingTime, String apartmentId) {
        return this.monthlyInvoiceRepository.existsByBillingTimeAndApartment_Id(billingTime, apartmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<MonthlyInvoiceResponse> findByApartment_Id(String apartmentId, Integer page, Integer limit) {
        Page<MonthlyInvoiceEntity> invoiceEntityPage = this.monthlyInvoiceRepository.findByApartment_Id(apartmentId, PaginationUtils.pagination(page, limit));
        return new PagedModel<>(invoiceEntityPage.map(monthlyInvoiceConvertor::entityToResponse));
    }
}
