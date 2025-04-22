package com.apartmentbuilding.PTIT.Service.WaterInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.WaterInvoice.WaterInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.WaterInvoiceEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IWaterInvoiceService {
    WaterInvoiceResponse save(WaterInvoiceRequest request);
    List<WaterInvoiceResponse> saveFromExcel(MultipartFile file);
    WaterInvoiceEntity findById(String id);
}
