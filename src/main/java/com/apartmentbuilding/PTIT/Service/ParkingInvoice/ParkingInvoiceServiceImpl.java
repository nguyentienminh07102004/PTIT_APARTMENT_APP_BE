package com.apartmentbuilding.PTIT.Service.ParkingInvoice;

import com.apartmentbuilding.PTIT.Common.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.DTO.Request.ParkingInvoice.ParkingInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Request.ParkingInvoice.ParkingInvoiceSearch;
import com.apartmentbuilding.PTIT.DTO.Response.ParkingInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.ParkingInvoice.ParkingInvoiceConvertor;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.ParkingInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.ParkingInvoiceEntity_;
import com.apartmentbuilding.PTIT.Model.Entity.UserEntity_;
import com.apartmentbuilding.PTIT.Repository.IParkingInvoiceRepository;
import com.apartmentbuilding.PTIT.Service.Apartment.IApartmentService;
import com.apartmentbuilding.PTIT.Service.MonthlyInvoice.IMonthlyInvoiceService;
import com.apartmentbuilding.PTIT.Utils.BillingTimeUtils;
import com.apartmentbuilding.PTIT.Utils.ExcelSheetIndex;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import com.apartmentbuilding.PTIT.Utils.ReadExcel;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
public class ParkingInvoiceServiceImpl implements IParkingInvoiceService {
    private final IParkingInvoiceRepository vehicleInvoiceRepository;
    private final ReadExcel requestReadExcel;
    private final ParkingInvoiceConvertor vehicleInvoiceConvertor;
    private final IMonthlyInvoiceService monthlyInvoiceService;
    private final IApartmentService apartmentService;

    @Override
    @Transactional
    public ParkingInvoiceEntity save(ParkingInvoiceRequest request) {
        ApartmentEntity apartment = this.apartmentService.findByName(request.getApartmentName());
        MonthlyInvoiceEntity monthlyInvoice = this.monthlyInvoiceService.findByBillingTimeAndApartment_Name(request.getBillingTime(), request.getApartmentName());
        if (monthlyInvoice == null) {
            monthlyInvoice = this.monthlyInvoiceService.save(MonthlyInvoiceEntity.builder()
                    .apartment(apartment)
                    .billingTime(request.getBillingTime())
                    .build());
        }
        ParkingInvoiceEntity packingInvoiceEntity = this.vehicleInvoiceConvertor.requestToEntity(request, monthlyInvoice);
        return this.vehicleInvoiceRepository.save(packingInvoiceEntity);
    }

    public void autoSave() {

    }

    @Override
    @Transactional
    public List<ParkingInvoiceEntity> save(MultipartFile file) {
        List<ParkingInvoiceRequest> invoiceRequests = this.requestReadExcel.readExcel(file, ExcelSheetIndex.getSheetIndex(ConstantConfig.VEHICLE_TYPE), ParkingInvoiceRequest.class);
        List<ParkingInvoiceEntity> result = new ArrayList<>();
        for (ParkingInvoiceRequest invoiceRequest : invoiceRequests) {
            result.add(this.save(invoiceRequest));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<ParkingInvoiceResponse> findByApartmentId(String apartmentId, Integer page, Integer limit) {
        Page<ParkingInvoiceEntity> entityPage = this.vehicleInvoiceRepository.findByMonthlyInvoice_Apartment_Id(apartmentId, PaginationUtils.pagination(page, limit));
        Page<ParkingInvoiceResponse> responsePage = entityPage.map(vehicleInvoiceConvertor::entityToResponse);
        return new PagedModel<>(responsePage);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<ParkingInvoiceResponse> findParkingInvoice(ParkingInvoiceSearch parkingInvoiceSearch) {
        Specification<ParkingInvoiceEntity> specification = (root, query, builder) -> {
            String request = parkingInvoiceSearch.getSearch();
            if (StringUtils.hasText(request)) {
                List<Predicate> predicates = new ArrayList<>();
                if (request.matches("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/([0-9]{4})$\n")) {
                    String[] dateYear = request.split("/");
                    predicates.add(builder.equal(root.get(MonthlyInvoiceEntity_.BILLING_TIME),
                            String.join("/", dateYear[1], dateYear[2])));
                } else if (BillingTimeUtils.isBillingTime(request)) {
                    predicates.add(builder.equal(root.get(MonthlyInvoiceEntity_.BILLING_TIME), request));
                } else {
                    predicates.add(builder.equal(root.get(MonthlyInvoiceEntity_.APARTMENT).get(ApartmentEntity_.NAME), request));
                }
                Predicate predicate = builder.or(predicates.toArray(new Predicate[0]));
                predicate = builder.and(predicate, builder.equal(root.get(ParkingInvoiceEntity_.MONTHLY_INVOICE).get(MonthlyInvoiceEntity_.APARTMENT)
                        .get(ApartmentEntity_.USER).get(UserEntity_.EMAIL), SecurityContextHolder.getContext().getAuthentication().getName()));
                if (query != null) {
                    query.distinct(true);
                }
                return predicate;
            }
            return builder.conjunction();
        };
        Page<ParkingInvoiceEntity> parkingInvoiceEntities = this.vehicleInvoiceRepository.findAll(specification, PaginationUtils.pagination(parkingInvoiceSearch.getPage(), parkingInvoiceSearch.getLimit()));
        return new PagedModel<>(parkingInvoiceEntities.map(this.vehicleInvoiceConvertor::entityToResponse));
    }
}
