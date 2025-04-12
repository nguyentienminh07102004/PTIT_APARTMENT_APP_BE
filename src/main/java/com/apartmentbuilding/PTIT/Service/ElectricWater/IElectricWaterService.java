package com.apartmentbuilding.PTIT.Service.ElectricWater;

import com.apartmentbuilding.PTIT.Common.Enum.TypeElectricWater;
import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Request.ElectrictWater.ElectricRequest;
import com.apartmentbuilding.PTIT.DTO.Request.ElectrictWater.WaterRequest;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricWaterInvoiceEntity;
import org.springframework.data.web.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IElectricWaterService {
    ElectricInvoiceResponse save(ElectricRequest request);

    ElectricWaterInvoiceEntity findById(String id);

    ElectricInvoiceResponse findResponseById(String id);

    List<ElectricInvoiceResponse> saveAllElectricInvoice(MultipartFile file);

    PagedModel<ElectricInvoiceResponse> findElectricByApartmentId(String apartmentId, Integer page, Integer limit);

    ElectricInvoiceResponse findByApartmentIdAndBillingTimeAndType(String apartmentId, String billingTime, TypeElectricWater type);

    // water
    WaterInvoiceResponse save(WaterRequest request);

    List<WaterInvoiceResponse> saveAllWaterInvoice(MultipartFile file);

    PagedModel<WaterInvoiceResponse> findWaterByApartmentId(String apartmentId, Integer page, Integer limit);
}
