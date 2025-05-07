package com.apartmentbuilding.PTIT.Service.WaterInvoice;

import com.apartmentbuilding.PTIT.Common.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Request.WaterInvoice.WaterInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.WaterInvoice.IWaterMapper;
import com.apartmentbuilding.PTIT.Mapper.WaterInvoice.WaterConvertor;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.WaterInvoiceEntity;
import com.apartmentbuilding.PTIT.Repository.IWaterRepository;
import com.apartmentbuilding.PTIT.Service.Apartment.IApartmentService;
import com.apartmentbuilding.PTIT.Service.MonthlyInvoice.IMonthlyInvoiceService;
import com.apartmentbuilding.PTIT.Utils.ExcelSheetIndex;
import com.apartmentbuilding.PTIT.Utils.ReadExcel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
}
