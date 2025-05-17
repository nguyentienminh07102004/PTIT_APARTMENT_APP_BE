package com.apartmentbuilding.PTIT.Service.ElectricInvoice;

import com.apartmentbuilding.PTIT.Common.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceSearch;
import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceUpdate;
import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.ElectricInvoice.ElectricConvertor;
import com.apartmentbuilding.PTIT.Mapper.ElectricInvoice.IElectricMapper;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricInvoiceEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity_;
import com.apartmentbuilding.PTIT.Repository.IElectricRepository;
import com.apartmentbuilding.PTIT.Service.Apartment.IApartmentService;
import com.apartmentbuilding.PTIT.Service.MonthlyInvoice.IMonthlyInvoiceService;
import com.apartmentbuilding.PTIT.Utils.BillingTimeUtils;
import com.apartmentbuilding.PTIT.Utils.ExcelSheetIndex;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import com.apartmentbuilding.PTIT.Utils.ReadExcel;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElectricInvoiceServiceImpl implements IElectricInvoiceService {
    private final IMonthlyInvoiceService monthlyInvoiceService;
    private final IApartmentService apartmentService;
    private final IElectricRepository electricRepository;
    private final IElectricMapper electricMapper;
    private final ReadExcel readExcel;
    private final ElectricConvertor electricConvertor;

    @Override
    @Transactional
    public ElectricInvoiceResponse save(ElectricInvoiceRequest request) {
        // check monthlyInvoice exists ?
        String billingTime = request.getBillingTime(); // format: MM/YYYY
        ApartmentEntity apartment = this.apartmentService.findByName(request.getApartmentName());
        MonthlyInvoiceEntity monthlyInvoice = this.monthlyInvoiceService.findByBillingTimeAndApartment_Name(billingTime, request.getApartmentName());
        ElectricInvoiceEntity invoice = this.electricMapper.requestToEntity(request);
        if (monthlyInvoice == null) {
            monthlyInvoice = this.monthlyInvoiceService.save(MonthlyInvoiceEntity.builder()
                    .apartment(apartment)
                    .billingTime(billingTime)
                    .build());
        }
        invoice.setMonthlyInvoice(monthlyInvoice);
        this.electricRepository.save(invoice);
        return this.electricConvertor.entityToResponse(invoice);
    }

    @Override
    @Transactional
    public ElectricInvoiceResponse updateInvoice(ElectricInvoiceUpdate invoiceUpdate) {
        ElectricInvoiceEntity invoice = this.findById(invoiceUpdate.getId());
        invoice.setCurrentNumber(invoiceUpdate.getCurrentNumber());
        invoice.setUnitPrice(invoiceUpdate.getUnitPrice());
        invoice.setStatus(invoiceUpdate.getStatus());
        this.electricRepository.save(invoice);
        return this.electricConvertor.entityToResponse(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public ElectricInvoiceEntity findById(String id) {
        return this.electricRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.ELECTRIC_INVOICE_NOT_FOUND));
    }

    @Override
    @Transactional
    public List<ElectricInvoiceResponse> saveAllElectricInvoice(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new DataInvalidException(ExceptionVariable.FILE_EMPTY);
        }
        List<ElectricInvoiceRequest> requestList = readExcel.readExcel(file, ExcelSheetIndex.getSheetIndex(ConstantConfig.ELECTRIC_TYPE), ElectricInvoiceRequest.class);
        List<ElectricInvoiceResponse> result = new ArrayList<>();
        for (ElectricInvoiceRequest request : requestList) {
            result.add(this.save(request));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<ElectricInvoiceResponse> findElectricInvoice(String search, Integer page, Integer limit) {
        Pageable pageable = PaginationUtils.pagination(page, limit);
        Specification<ElectricInvoiceEntity> specification = (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(search)) {
                return criteriaBuilder.conjunction();
            }
            List<Predicate> predicates = new ArrayList<>();
            if (search.matches("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/([0-9]{4})$\n")) {
                // tách lấy ngày tháng
                String[] dateYear = search.split("/");
                predicates.add(criteriaBuilder.equal(root.get(ElectricInvoiceEntity_.MONTHLY_INVOICE)
                        .get(MonthlyInvoiceEntity_.BILLING_TIME), String.join("/", dateYear[1], dateYear[2])));
            } else if (BillingTimeUtils.isBillingTime(search)) {
                predicates.add(criteriaBuilder.equal(root.get(ElectricInvoiceEntity_.MONTHLY_INVOICE)
                        .get(MonthlyInvoiceEntity_.BILLING_TIME), search));
            } else {
                predicates.add(criteriaBuilder.like(root.get(ElectricInvoiceEntity_.MONTHLY_INVOICE)
                        .get(MonthlyInvoiceEntity_.APARTMENT)
                        .get(ApartmentEntity_.NAME), "%" + search + "%"));
            }
            if (query != null) {
                query.distinct(true);
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
        return new PagedModel<>(this.electricRepository.findAll(specification, pageable)
                .map(this.electricConvertor::entityToResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<ElectricInvoiceResponse> findElectricInvoice(ElectricInvoiceSearch search) {
        Pageable pageable = PaginationUtils.pagination(search.getPage(), search.getLimit());
        Specification<ElectricInvoiceEntity> specification = (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (StringUtils.hasText(search.getBillingTime())) {
                predicate = builder.and(builder.equal(root.get(ElectricInvoiceEntity_.MONTHLY_INVOICE)
                        .get(MonthlyInvoiceEntity_.BILLING_TIME), search.getBillingTime()));
            }
            if (StringUtils.hasText(search.getApartmentName())) {
                predicate = builder.and(builder.equal(root.get(ElectricInvoiceEntity_.MONTHLY_INVOICE)
                        .get(MonthlyInvoiceEntity_.APARTMENT)
                        .get(ApartmentEntity_.NAME), search.getApartmentName()));
            }
            if (query != null) {
                //query.distinct(true);
                query.orderBy(
                        builder.asc(root.get(ElectricInvoiceEntity_.MONTHLY_INVOICE).get(MonthlyInvoiceEntity_.BILLING_TIME)),
                        builder.asc(root.get(ElectricInvoiceEntity_.MONTHLY_INVOICE).get(MonthlyInvoiceEntity_.APARTMENT).get(ApartmentEntity_.NAME))
                );
            }
            return predicate;
        };
        return new PagedModel<>(this.electricRepository.findAll(specification, pageable)
                .map(this.electricConvertor::entityToResponse));
    }
}
