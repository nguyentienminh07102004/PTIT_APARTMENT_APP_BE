package com.apartmentbuilding.PTIT.Service.VehicleInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.VehicleInvoice.VehicleInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.VehicleInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleInvoiceEntity;
import org.springframework.data.web.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IVehicleInvoiceService {
    VehicleInvoiceEntity save(VehicleInvoiceRequest request);

    List<VehicleInvoiceEntity> save(MultipartFile file);

    PagedModel<VehicleInvoiceResponse> findByApartmentId(String apartmentId, Integer page, Integer limit);
}
