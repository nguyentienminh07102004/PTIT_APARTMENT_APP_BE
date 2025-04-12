package com.apartmentbuilding.PTIT.Service.VehicleInvoice;

import com.apartmentbuilding.PTIT.Common.Enum.PaymentStatus;
import com.apartmentbuilding.PTIT.DTO.Request.VehicleInvoice.VehicleInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.VehicleInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleInvoiceEntity;
import com.apartmentbuilding.PTIT.Mapper.VehicleInvoice.VehicleInvoiceConvertor;
import com.apartmentbuilding.PTIT.Repository.IVehicleInvoiceRepository;
import com.apartmentbuilding.PTIT.Service.Apartment.IApartmentService;
import com.apartmentbuilding.PTIT.Service.MonthlyInvoice.IMonthlyInvoiceService;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import com.apartmentbuilding.PTIT.Utils.ReadExcel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleInvoiceServiceImpl implements IVehicleInvoiceService {
    private final IVehicleInvoiceRepository vehicleInvoiceRepository;
    private final ReadExcel<VehicleInvoiceRequest> requestReadExcel;
    private final VehicleInvoiceConvertor vehicleInvoiceConvertor;
    private final IMonthlyInvoiceService monthlyInvoiceService;
    private final IApartmentService apartmentService;

    @Override
    @Transactional
    public VehicleInvoiceEntity save(VehicleInvoiceRequest request) {
        ApartmentEntity apartment = apartmentService.findById(request.getApartmentId());
        MonthlyInvoiceEntity monthlyInvoice = monthlyInvoiceService.findByBillingTimeAndApartment_Id(request.getBillingTime(), request.getApartmentId());
        if (monthlyInvoice == null) {
            monthlyInvoice = monthlyInvoiceService.save(MonthlyInvoiceEntity.builder()
                    .createdDate(new Date(System.currentTimeMillis()))
                    .status(PaymentStatus.PENDING)
                    .apartment(apartment)
                    .build());
        }
        VehicleInvoiceEntity vehicleInvoiceEntity = vehicleInvoiceConvertor.requestToEntity(request, monthlyInvoice);
        return vehicleInvoiceRepository.save(vehicleInvoiceEntity);
    }


    @Override
    @Transactional
    public List<VehicleInvoiceEntity> save(MultipartFile file) {
        List<VehicleInvoiceRequest> invoiceRequests = requestReadExcel.readExcel(file, 0, VehicleInvoiceRequest.class);
        List<VehicleInvoiceEntity> result = new ArrayList<>();
        for (VehicleInvoiceRequest invoiceRequest : invoiceRequests) {
            result.add(this.save(invoiceRequest));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<VehicleInvoiceResponse> findByApartmentId(String apartmentId, Integer page, Integer limit) {
        Page<VehicleInvoiceEntity> entityPage = vehicleInvoiceRepository.findByMonthlyInvoice_Apartment_Id(apartmentId, PaginationUtils.pagination(page, limit));
        Page<VehicleInvoiceResponse> responsePage = entityPage.map(vehicleInvoiceConvertor::entityToResponse);
        return new PagedModel<>(responsePage);
    }
}
