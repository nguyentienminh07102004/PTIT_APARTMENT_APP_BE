package com.apartmentbuilding.PTIT.Service.ServiceInvoice;

import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Request.ServiceInvoice.ServiceInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ServiceInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.ServiceInvoice.ServiceInvoiceConverter;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.ServiceInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.ServiceTypeEntity;
import com.apartmentbuilding.PTIT.Repository.IServiceInvoiceRepository;
import com.apartmentbuilding.PTIT.Service.Apartment.IApartmentService;
import com.apartmentbuilding.PTIT.Service.MonthlyInvoice.IMonthlyInvoiceService;
import com.apartmentbuilding.PTIT.Service.ServiceType.IServiceType;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServiceInvoiceServiceImpl implements IServiceInvoiceService {
    private final IServiceInvoiceRepository serviceInvoiceRepository;
    private final IApartmentService apartmentService;
    private final IMonthlyInvoiceService monthlyInvoiceService;
    private final IServiceType serviceType;
    private final ServiceInvoiceConverter serviceInvoiceConverter;

    @Override
    @Transactional
    public ServiceInvoiceResponse save(ServiceInvoiceRequest request) {
        ServiceInvoiceEntity entity = new ServiceInvoiceEntity();
        ApartmentEntity apartment = this.apartmentService.findByName(request.getApartmentName());
        MonthlyInvoiceEntity monthlyInvoice = this.monthlyInvoiceService.findByBillingTimeAndApartment_Name(request.getBillingTime(), request.getApartmentName());
        ServiceTypeEntity serviceTypeEntity = this.serviceType.findByName(request.getTypeName());
        entity.setUnitPrice(serviceTypeEntity.getUnitPrice());
        entity.setType(serviceTypeEntity);
        if (monthlyInvoice == null) {
            monthlyInvoice = this.monthlyInvoiceService.save(MonthlyInvoiceEntity.builder()
                    .apartment(apartment)
                    .billingTime(request.getBillingTime())
                    .build());
        }
        entity.setMonthlyInvoice(monthlyInvoice);
        this.serviceInvoiceRepository.save(entity);
        return this.serviceInvoiceConverter.toResponse(entity);
    }

    @Override
    @Transactional
    public ServiceInvoiceEntity findById(String id) {
        return this.serviceInvoiceRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.SERVICE_INVOICE_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<ServiceInvoiceResponse> findMyInvoices(Integer page, Integer limit) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable pageable = PaginationUtils.pagination(page, limit);
        Page<ServiceInvoiceEntity> serviceInvoices = this.serviceInvoiceRepository.findDistinctByMonthlyInvoice_Apartment_User_Email(email, pageable);
        return new PagedModel<>(serviceInvoices.map(this.serviceInvoiceConverter::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<ServiceInvoiceResponse> findAllInvoices(Integer page, Integer limit) {
        Pageable pageable = PaginationUtils.pagination(page, limit);
        Page<ServiceInvoiceEntity> serviceInvoices = this.serviceInvoiceRepository.findAll(pageable);
        return new PagedModel<>(serviceInvoices.map(this.serviceInvoiceConverter::toResponse));
    }
}
