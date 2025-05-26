package com.apartmentbuilding.PTIT.Service.WaterInvoice;

import com.apartmentbuilding.PTIT.Common.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Request.WaterInvoice.WaterInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Request.WaterInvoice.WaterInvoiceSearchRequest;
import com.apartmentbuilding.PTIT.DTO.Request.WaterInvoice.WaterInvoiceUpdate;
import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.WaterInvoice.IWaterMapper;
import com.apartmentbuilding.PTIT.Mapper.WaterInvoice.WaterConvertor;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricInvoiceEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.UserEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.WaterInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.WaterInvoiceEntity_;
import com.apartmentbuilding.PTIT.Repository.IWaterRepository;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WaterInvoiceServiceImpl implements IWaterInvoiceService {
    private final IWaterRepository waterRepository;
    private final IWaterMapper waterMapper;
    private final WaterConvertor waterConvertor;
    private final IApartmentService apartmentService;
    private final IMonthlyInvoiceService monthlyInvoiceService;
    private final ReadExcel readExcel;

    @Override
    public WaterInvoiceResponse save(WaterInvoiceRequest request) {
        WaterInvoiceEntity waterInvoice = this.waterMapper.requestToEntity(request);
        String billingTime = request.getBillingTime(); // format: MM/YYYY
        ApartmentEntity apartment = this.apartmentService.findByName(request.getApartmentName());
        MonthlyInvoiceEntity monthlyInvoice = this.monthlyInvoiceService.findByBillingTimeAndApartment_Name(billingTime, request.getApartmentName());
        if (monthlyInvoice == null) {
            monthlyInvoice = this.monthlyInvoiceService.save(MonthlyInvoiceEntity.builder()
                    .apartment(apartment)
                    .billingTime(billingTime)
                    .build());
        }
        waterInvoice.setMonthlyInvoice(monthlyInvoice);
        this.waterRepository.save(waterInvoice);
        return this.waterConvertor.entityToResponse(waterInvoice);
    }

    @Override
    @Transactional
    public List<WaterInvoiceResponse> saveFromExcel(MultipartFile file) {
        List<WaterInvoiceRequest> waterRequests = this.readExcel.readExcel(file, ExcelSheetIndex.getSheetIndex(ConstantConfig.WATER_TYPE), WaterInvoiceRequest.class);
        List<WaterInvoiceResponse> responses = new ArrayList<>();
        for (WaterInvoiceRequest waterRequest : waterRequests) {
            responses.add(this.save(waterRequest));
        }
        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public WaterInvoiceEntity findById(String id) {
        return this.waterRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.WATER_INVOICE_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<WaterInvoiceResponse> findWaterInvoice(WaterInvoiceSearchRequest search) {
        Pageable pageable = PaginationUtils.pagination(search.getPage(), search.getLimit());
        Specification<WaterInvoiceEntity> specification = (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (StringUtils.hasText(search.getBillingTime())) {
                predicate = builder.and(builder.equal(root.get(WaterInvoiceEntity_.MONTHLY_INVOICE)
                        .get(MonthlyInvoiceEntity_.BILLING_TIME), search.getBillingTime()));
            }
            if (StringUtils.hasText(search.getApartmentName())) {
                predicate = builder.and(builder.equal(root.get(WaterInvoiceEntity_.MONTHLY_INVOICE)
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
        return new PagedModel<>(this.waterRepository.findAll(specification, pageable)
                .map(this.waterConvertor::entityToResponse));
    }

    @Override
    public PagedModel<WaterInvoiceResponse> findMyWaterInvoice(String search, Integer page, Integer limit) {
        Pageable pageable = PaginationUtils.pagination(page, limit);
        Specification<WaterInvoiceEntity> specification = (root, query, criteriaBuilder) -> {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            if (!StringUtils.hasText(search)) {
                return criteriaBuilder.equal(root.get(WaterInvoiceEntity_.MONTHLY_INVOICE)
                        .get(MonthlyInvoiceEntity_.APARTMENT)
                        .get(ApartmentEntity_.USER)
                        .get(UserEntity_.EMAIL), email);
            }
            List<Predicate> predicates = new ArrayList<>();
            if (BillingTimeUtils.isBillingTime(search)) {
                predicates.add(criteriaBuilder.equal(root.get(ElectricInvoiceEntity_.MONTHLY_INVOICE)
                        .get(MonthlyInvoiceEntity_.BILLING_TIME), search));
            } else {
                predicates.add(criteriaBuilder.like(root.get(ElectricInvoiceEntity_.MONTHLY_INVOICE)
                        .get(MonthlyInvoiceEntity_.APARTMENT)
                        .get(ApartmentEntity_.NAME), "%" + search + "%"));
            }
            if (query != null) {
                //query.distinct(true);
                query.orderBy(criteriaBuilder.desc(root.get(WaterInvoiceEntity_.MONTHLY_INVOICE).get(MonthlyInvoiceEntity_.BILLING_TIME)));
            }
            Predicate predicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(ElectricInvoiceEntity_.MONTHLY_INVOICE)
                    .get(MonthlyInvoiceEntity_.APARTMENT)
                    .get(ApartmentEntity_.USER)
                    .get(UserEntity_.EMAIL), email));
            return predicate;
        };
        return new PagedModel<>(this.waterRepository.findAll(specification, pageable)
                .map(this.waterConvertor::entityToResponse));
    }

    @Override
    @Transactional
    public WaterInvoiceResponse updateWaterInvoice(WaterInvoiceUpdate request) {
        WaterInvoiceEntity waterInvoice = this.findById(request.getId());
        waterInvoice.setCurrentNumber(request.getCurrentNumber());
        waterInvoice.setUnitPrice(request.getUnitPrice());
        this.waterRepository.save(waterInvoice);
        return this.waterConvertor.entityToResponse(waterInvoice);
    }
}
