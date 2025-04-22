package com.apartmentbuilding.PTIT.Service.ElectricInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricInvoiceEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IElectricInvoiceService {
    ElectricInvoiceResponse save(ElectricInvoiceRequest request);
    List<ElectricInvoiceResponse> saveAllElectricInvoice(MultipartFile file);
    ElectricInvoiceEntity findById(String id);
}
