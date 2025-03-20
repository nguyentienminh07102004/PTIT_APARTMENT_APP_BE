package com.apartmentbuilding.PTIT.Service.ElectricWater;

import com.apartmentbuilding.PTIT.Beans.PaymentStatus;
import com.apartmentbuilding.PTIT.Beans.TypeElectricWater;
import com.apartmentbuilding.PTIT.DTO.Reponse.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Request.ElectrictWater.ElectricRequest;
import com.apartmentbuilding.PTIT.Domains.ApartmentEntity;
import com.apartmentbuilding.PTIT.Domains.ElectricWaterInvoiceEntity;
import com.apartmentbuilding.PTIT.Domains.MonthlyInvoiceEntity;
import com.apartmentbuilding.PTIT.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.ExceptionAdvice.ExceptionVariable;
import com.apartmentbuilding.PTIT.Mapper.Electric.ElectricConvertor;
import com.apartmentbuilding.PTIT.Mapper.Electric.ElectricMapper;
import com.apartmentbuilding.PTIT.Repository.IElectricWaterRepository;
import com.apartmentbuilding.PTIT.Service.Apartment.IApartmentService;
import com.apartmentbuilding.PTIT.Service.MonthlyInvoice.IMonthlyInvoiceService;
import com.apartmentbuilding.PTIT.Utils.ExcelSheetIndex;
import com.apartmentbuilding.PTIT.Utils.ReadExcel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.apartmentbuilding.PTIT.Beans.ConstantConfig.WATER_TYPE;

@Service
@RequiredArgsConstructor
public class ElectricServiceImpl implements IElectricWaterService {
    private final ElectricMapper electricMapper;
    private final IElectricWaterRepository electricWaterRepository;
    private final IMonthlyInvoiceService monthlyInvoiceService;
    private final IApartmentService apartmentService;
    private final ReadExcel<ElectricRequest> requestReadExcel;
    private final ElectricConvertor electricConvertor;

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
        List<ElectricRequest> requestList = requestReadExcel.readExcel(file, ExcelSheetIndex.getSheetIndex(WATER_TYPE), ElectricRequest.class);
        List<ElectricInvoiceResponse> result = new ArrayList<>();
        for (ElectricRequest request : requestList) {
            result.add(this.save(request));
        }
        return result;
    }

    @Override
    public List<ElectricInvoiceResponse> findByApartmentId(String apartmentId) {
        List<ElectricWaterInvoiceEntity> entities = this.electricWaterRepository.findDistinctByMonthlyInvoice_Apartment_Id(apartmentId);
        return entities.stream().map(electricMapper::entityToResponse).toList();
    }

    @Override
    public ElectricInvoiceResponse findByApartmentIdAndBillingTimeAndType(String apartmentId, String billingTime, TypeElectricWater type) {
        ElectricWaterInvoiceEntity entity = this.electricWaterRepository.findDistinctByMonthlyInvoice_Apartment_IdAndMonthlyInvoice_BillingTimeAndType(apartmentId, billingTime, type);
        return electricConvertor.entityToResponse(entity);
    }
}
