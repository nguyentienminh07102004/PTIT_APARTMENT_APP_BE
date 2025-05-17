package com.apartmentbuilding.PTIT.Service.ElectricInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceSearch;
import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceUpdate;
import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricInvoiceEntity;
import org.springframework.data.web.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IElectricInvoiceService {
    ElectricInvoiceResponse save(ElectricInvoiceRequest request);
    ElectricInvoiceResponse updateInvoice(ElectricInvoiceUpdate invoiceUpdate);
    List<ElectricInvoiceResponse> saveAllElectricInvoice(MultipartFile file);
    PagedModel<ElectricInvoiceResponse> findElectricInvoice(String search, Integer page, Integer size);
    PagedModel<ElectricInvoiceResponse> findElectricInvoice(ElectricInvoiceSearch search);
    ElectricInvoiceEntity findById(String id);
}
