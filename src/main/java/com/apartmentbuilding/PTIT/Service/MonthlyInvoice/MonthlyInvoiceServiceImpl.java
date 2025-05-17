package com.apartmentbuilding.PTIT.Service.MonthlyInvoice;

import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Response.MonthlyInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.MonthlyInvoice.MonthlyInvoiceConvertor;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity_;
import com.apartmentbuilding.PTIT.Repository.IMonthlyInvoiceRepository;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

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
    @Transactional(readOnly = true)
    public MonthlyInvoiceResponse findResponseById(String id) {
        return this.monthlyInvoiceConvertor.entityToResponse(this.findById(id));
    }

    @Override
    @Transactional
    public MonthlyInvoiceEntity save(MonthlyInvoiceEntity monthlyInvoice) {
        return this.monthlyInvoiceRepository.save(monthlyInvoice);
    }

    @Override
    @Transactional(readOnly = true)
    public MonthlyInvoiceEntity findByBillingTimeAndApartment_Name(String billingTime, String apartmentName) {
        return this.monthlyInvoiceRepository.findByBillingTimeAndApartment_Name(billingTime, apartmentName);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByBillingTimeAndApartment_Id(String billingTime, String apartmentId) {
        return this.monthlyInvoiceRepository.existsByBillingTimeAndApartment_Id(billingTime, apartmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<MonthlyInvoiceResponse> findByApartment_Name(String apartmentName, Integer page, Integer limit) {
        Page<MonthlyInvoiceEntity> invoiceEntityPage = this.monthlyInvoiceRepository.findByApartment_Name(apartmentName, PaginationUtils.pagination(page, limit));
        return new PagedModel<>(invoiceEntityPage.map(this.monthlyInvoiceConvertor::entityToResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<MonthlyInvoiceResponse> findMyInvoice(Integer page, Integer limit) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PaginationUtils.pagination(page, limit, Map.of(MonthlyInvoiceEntity_.BILLING_TIME, Sort.Direction.DESC));
        Page<MonthlyInvoiceEntity> invoiceEntity = this.monthlyInvoiceRepository.findDistinctByApartment_User_Email(email, pageable);
        return new PagedModel<>(invoiceEntity.map(this.monthlyInvoiceConvertor::entityToResponse));
    }
}
