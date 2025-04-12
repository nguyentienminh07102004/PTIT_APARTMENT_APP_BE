package com.apartmentbuilding.PTIT.Service.ElectricWater;

import com.apartmentbuilding.PTIT.Common.Enum.PaymentStatus;
import com.apartmentbuilding.PTIT.Common.Enum.TypeElectricWater;
import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Request.ElectrictWater.ElectricRequest;
import com.apartmentbuilding.PTIT.DTO.Request.ElectrictWater.WaterRequest;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricWaterInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.ExceptionVariable;
import com.apartmentbuilding.PTIT.Mapper.Electric.ElectricConvertor;
import com.apartmentbuilding.PTIT.Mapper.Electric.ElectricMapper;
import com.apartmentbuilding.PTIT.Mapper.Water.WaterConvertor;
import com.apartmentbuilding.PTIT.Mapper.Water.WaterMapper;
import com.apartmentbuilding.PTIT.Repository.IElectricWaterRepository;
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
import java.util.Date;
import java.util.List;

import static com.apartmentbuilding.PTIT.Common.Beans.ConstantConfig.WATER_TYPE;

@Service
@RequiredArgsConstructor
public class ElectricWaterServiceImpl implements IElectricWaterService {
    private final ElectricMapper electricMapper;
    private final WaterMapper waterMapper;
    private final IElectricWaterRepository electricWaterRepository;
    private final IMonthlyInvoiceService monthlyInvoiceService;
    private final IApartmentService apartmentService;
    private final ReadExcel<ElectricRequest> electricRequestReadExcel;
    private final ReadExcel<WaterRequest> waterRequestReadExcel;
    private final ElectricConvertor electricConvertor;
    private final WaterConvertor waterConvertor;

    // electric
    @Override
    @Transactional
    public ElectricInvoiceResponse save(ElectricRequest request) {
        ElectricWaterInvoiceEntity entity = electricMapper.requestToEntity(request);
        entity.setType(TypeElectricWater.ELECTRIC);
        // check monthlyInvoice exists ?
        String billingTime = request.getBillingTime(); // format: MM/YYYY
        MonthlyInvoiceEntity monthlyInvoice = monthlyInvoiceService.findByBillingTimeAndApartment_Id(billingTime, request.getApartmentId());
        ApartmentEntity apartment = apartmentService.findById(request.getApartmentId());
        if (monthlyInvoice == null) {
            monthlyInvoice = monthlyInvoiceService.save(MonthlyInvoiceEntity.builder()
                            .createdDate(new Date(System.currentTimeMillis()))
                            .status(PaymentStatus.PENDING)
                            .apartment(apartment)
                    .build());
        }
        entity.setMonthlyInvoice(monthlyInvoice);
        electricWaterRepository.save(entity);
        return electricConvertor.entityToResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ElectricWaterInvoiceEntity findById(String id) {
        return electricWaterRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.ELECTRIC_WATER_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public ElectricInvoiceResponse findResponseById(String id) {
        return electricConvertor.entityToResponse(this.findById(id));
    }

    @Override
    @Transactional
    public List<ElectricInvoiceResponse> saveAllElectricInvoice(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new DataInvalidException(ExceptionVariable.FILE_EMPTY);
        }
        List<ElectricRequest> requestList = electricRequestReadExcel.readExcel(file, ExcelSheetIndex.getSheetIndex(WATER_TYPE), ElectricRequest.class);
        List<ElectricInvoiceResponse> result = new ArrayList<>();
        for (ElectricRequest request : requestList) {
            result.add(this.save(request));
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<ElectricInvoiceResponse> findElectricByApartmentId(String apartmentId, Integer page, Integer limit) {
        Page<ElectricWaterInvoiceEntity> entities = this.electricWaterRepository.findDistinctByMonthlyInvoice_Apartment_IdAndType(apartmentId, TypeElectricWater.ELECTRIC, PaginationUtils.pagination(page, limit));
        Page<ElectricInvoiceResponse> electricInvoiceResponses = entities.map(electricConvertor::entityToResponse);
        return new PagedModel<>(electricInvoiceResponses);
    }

    @Override
    @Transactional(readOnly = true)
    public ElectricInvoiceResponse findByApartmentIdAndBillingTimeAndType(String apartmentId, String billingTime, TypeElectricWater type) {
        ElectricWaterInvoiceEntity entity = this.electricWaterRepository.findDistinctByMonthlyInvoice_Apartment_IdAndMonthlyInvoice_BillingTimeAndType(apartmentId, billingTime, type);
        return electricConvertor.entityToResponse(entity);
    }

    // water
    @Override
    @Transactional
    public WaterInvoiceResponse save(WaterRequest request) {
        ElectricWaterInvoiceEntity entity = waterMapper.requestToEntity(request);
        entity.setType(TypeElectricWater.WATER);
        String billingTime = request.getBillingTime();
        ApartmentEntity apartment = apartmentService.findById(request.getApartmentId());
        MonthlyInvoiceEntity monthlyInvoice = monthlyInvoiceService.findByBillingTimeAndApartment_Id(billingTime, request.getApartmentId());
        if (monthlyInvoice == null) {
            monthlyInvoice = MonthlyInvoiceEntity.builder()
                    .createdDate(new Date(System.currentTimeMillis()))
                    .status(PaymentStatus.PENDING)
                    .apartment(apartment)
                    .build();
            monthlyInvoiceService.save(monthlyInvoice);
        }
        entity.setMonthlyInvoice(monthlyInvoice);
        electricWaterRepository.save(entity);
        return waterConvertor.entityToResponse(entity);
    }

    @Override
    @Transactional
    public List<WaterInvoiceResponse> saveAllWaterInvoice(MultipartFile file) {
        List<WaterRequest> waterRequests = waterRequestReadExcel.readExcel(file, ExcelSheetIndex.getSheetIndex(WATER_TYPE), WaterRequest.class);
        List<WaterInvoiceResponse> responses = new ArrayList<>();
        for (WaterRequest request : waterRequests) {
            responses.add(this.save(request));
        }
        return responses;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<WaterInvoiceResponse> findWaterByApartmentId(String apartmentId, Integer page, Integer limit) {
        Page<ElectricWaterInvoiceEntity> list = electricWaterRepository.findDistinctByMonthlyInvoice_Apartment_IdAndType(apartmentId, TypeElectricWater.WATER, PaginationUtils.pagination(page, limit));
        Page<WaterInvoiceResponse> waterInvoiceResponses = list.map(waterConvertor::entityToResponse);
        return new PagedModel<>(waterInvoiceResponses);
    }
}
