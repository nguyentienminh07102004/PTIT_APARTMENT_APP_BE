package com.apartmentbuilding.PTIT.Service.MonthlyInvoice;

import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Request.MonthInvoice.MonthInvoiceSearch;
import com.apartmentbuilding.PTIT.DTO.Response.MonthlyInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.MonthlyInvoice.MonthlyInvoiceConvertor;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Repository.IMonthlyInvoiceRepository;
import com.apartmentbuilding.PTIT.Utils.BillingTimeUtils;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity_;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    public PagedModel<MonthlyInvoiceResponse> findMyInvoice(MonthInvoiceSearch search) {
        Specification<MonthlyInvoiceEntity> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String request = search.getSearch();
            if (request.matches("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/([0-9]{4})$\n")) {
                // tách lấy ngày tháng
                String[] dateYear = request.split("/");
                predicates.add(criteriaBuilder.equal(root.get(MonthlyInvoiceEntity_.BILLING_TIME),
                        String.join("/", dateYear[1], dateYear[2])));
            } else if (BillingTimeUtils.isBillingTime(request)) {
                predicates.add(criteriaBuilder.equal(root.get(MonthlyInvoiceEntity_.BILLING_TIME), request));
            } else {
                predicates.add(criteriaBuilder.equal(root.get(MonthlyInvoiceEntity_.APARTMENT).get(ApartmentEntity_.NAME), request));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PaginationUtils.pagination(search.getPage(), search.getLimit(), Map.of(MonthlyInvoiceEntity_.BILLING_TIME, Sort.Direction.DESC));
        Page<MonthlyInvoiceEntity> invoiceEntity = this.monthlyInvoiceRepository.findDistinctByApartment_User_Email(email, pageable);
        return new PagedModel<>(invoiceEntity.map(this.monthlyInvoiceConvertor::entityToResponse));
    }
}
