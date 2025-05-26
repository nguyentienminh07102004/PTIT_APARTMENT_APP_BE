package com.apartmentbuilding.PTIT.Service.MonthlyInvoice;

import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.Enums.PaymentMethod;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Request.MonthInvoice.MonthInvoiceSearch;
import com.apartmentbuilding.PTIT.DTO.Response.MonthlyInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.MonthlyInvoice.MonthlyInvoiceConvertor;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.UserEntity_;
import com.apartmentbuilding.PTIT.Repository.IMonthlyInvoiceRepository;
import com.apartmentbuilding.PTIT.Service.Apartment.IApartmentService;
import com.apartmentbuilding.PTIT.Utils.BillingTimeUtils;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthlyInvoiceServiceImpl implements IMonthlyInvoiceService {
    private final IMonthlyInvoiceRepository monthlyInvoiceRepository;
    private final MonthlyInvoiceConvertor monthlyInvoiceConvertor;
    private final IApartmentService apartmentService;

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
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            List<Predicate> predicates = new ArrayList<>();
            String request = search.getSearch();
            if (!StringUtils.hasText(request)) {
                return criteriaBuilder.equal(root.get(MonthlyInvoiceEntity_.APARTMENT)
                        .get(ApartmentEntity_.USER)
                        .get(UserEntity_.EMAIL), email);
            }
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
            Predicate predicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(MonthlyInvoiceEntity_.APARTMENT)
                    .get(ApartmentEntity_.USER)
                    .get(UserEntity_.EMAIL), email));
            if (query != null) query.distinct(true);
            return predicate;
        };
        Pageable pageable = PaginationUtils.pagination(search.getPage(), search.getLimit(), Sort.by(Sort.Direction.DESC, MonthlyInvoiceEntity_.BILLING_TIME));
        Page<MonthlyInvoiceEntity> invoiceEntity = this.monthlyInvoiceRepository.findAll(specification, pageable);
        return new PagedModel<>(invoiceEntity.map(this.monthlyInvoiceConvertor::entityToResponse));
    }

    @Override
    public List<MonthlyInvoiceResponse> findInvoicesChart() {
        List<MonthlyInvoiceResponse> responses = new ArrayList<>();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<String> apartmentNames = this.apartmentService.getAllApartmentNameByUserEmail(email);
        for (String apartmentName: apartmentNames) {
            List<MonthlyInvoiceEntity> monthlyInvoices = this.monthlyInvoiceRepository.findTop6ByApartment_Name(apartmentName);
            responses.addAll(monthlyInvoices.stream()
                    .map(this.monthlyInvoiceConvertor::entityToResponse)
                    .toList());
        }
        return responses;
    }

    @Override
    @Transactional
    public MonthlyInvoiceResponse pay(String id) {
        MonthlyInvoiceEntity entity = this.findById(id);
        entity.setPaymentDate(new Date(System.currentTimeMillis()));
        entity.setPaymentMethod(PaymentMethod.CAST);
        this.monthlyInvoiceRepository.save(entity);
        return this.monthlyInvoiceConvertor.entityToResponse(entity);
    }
}
