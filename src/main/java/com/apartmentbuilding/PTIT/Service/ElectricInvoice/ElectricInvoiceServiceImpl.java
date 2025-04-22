package com.apartmentbuilding.PTIT.Service.ElectricInvoice;

import com.apartmentbuilding.PTIT.Common.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.Mapper.ElectricInvoice.ElectricConvertor;
import com.apartmentbuilding.PTIT.Mapper.ElectricInvoice.IElectricMapper;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricInvoiceEntity;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.Repository.IElectricRepository;
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
        ApartmentEntity apartment = this.apartmentService.findById(request.getApartmentId());
        MonthlyInvoiceEntity monthlyInvoice = this.monthlyInvoiceService.findByBillingTimeAndApartment_Id(billingTime, request.getApartmentId());
        ElectricInvoiceEntity invoice = this.electricMapper.requestToEntity(request);
        if (monthlyInvoice == null) {
            monthlyInvoice = this.monthlyInvoiceService.save(MonthlyInvoiceEntity.builder()
                            .apartment(apartment)
                    .build());
        }
        invoice.setMonthlyInvoice(monthlyInvoice);
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
}
