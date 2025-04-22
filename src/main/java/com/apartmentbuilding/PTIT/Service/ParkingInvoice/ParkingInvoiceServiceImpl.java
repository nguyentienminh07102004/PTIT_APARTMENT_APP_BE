package com.apartmentbuilding.PTIT.Service.ParkingInvoice;

import com.apartmentbuilding.PTIT.Common.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.DTO.Request.VehicleInvoice.VehicleInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.VehicleInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.ParkingInvoice.ParkingInvoiceConvertor;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.ParkingInvoiceEntity;
import com.apartmentbuilding.PTIT.Repository.IParkingInvoiceRepository;
import com.apartmentbuilding.PTIT.Service.Apartment.IApartmentService;
import com.apartmentbuilding.PTIT.Service.MonthlyInvoice.IMonthlyInvoiceService;
import com.apartmentbuilding.PTIT.Utils.ExcelSheetIndex;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import com.apartmentbuilding.PTIT.Utils.ReadExcel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public ParkingInvoiceEntity save(VehicleInvoiceRequest request) {
        ApartmentEntity apartment = this.apartmentService.findById(request.getApartmentId());
        MonthlyInvoiceEntity monthlyInvoice = this.monthlyInvoiceService.findByBillingTimeAndApartment_Id(request.getBillingTime(), request.getApartmentId());
        if (monthlyInvoice == null) {
            monthlyInvoice = this.monthlyInvoiceService.save(MonthlyInvoiceEntity.builder()
                    .apartment(apartment)
                    .build());
        }
        ParkingInvoiceEntity packingInvoiceEntity = this.vehicleInvoiceConvertor.requestToEntity(request, monthlyInvoice);
        return this.vehicleInvoiceRepository.save(packingInvoiceEntity);
    }


    @Override
    @Transactional
    public List<ParkingInvoiceEntity> save(MultipartFile file) {
        List<VehicleInvoiceRequest> invoiceRequests = this.requestReadExcel.readExcel(file, ExcelSheetIndex.getSheetIndex(ConstantConfig.VEHICLE_TYPE), VehicleInvoiceRequest.class);
        List<ParkingInvoiceEntity> result = new ArrayList<>();
        for (VehicleInvoiceRequest invoiceRequest : invoiceRequests) {
            result.add(this.save(invoiceRequest));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<VehicleInvoiceResponse> findByApartmentId(String apartmentId, Integer page, Integer limit) {
        Page<ParkingInvoiceEntity> entityPage = this.vehicleInvoiceRepository.findByMonthlyInvoice_Apartment_Id(apartmentId, PaginationUtils.pagination(page, limit));
        Page<VehicleInvoiceResponse> responsePage = entityPage.map(vehicleInvoiceConvertor::entityToResponse);
        return new PagedModel<>(responsePage);
    }
}
