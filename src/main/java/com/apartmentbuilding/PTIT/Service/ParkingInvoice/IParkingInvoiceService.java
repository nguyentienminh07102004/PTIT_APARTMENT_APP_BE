package com.apartmentbuilding.PTIT.Service.ParkingInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.VehicleInvoice.ParkingInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.VehicleInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ParkingInvoiceEntity;
import org.springframework.data.web.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IParkingInvoiceService {
    ParkingInvoiceEntity save(ParkingInvoiceRequest request);
    List<ParkingInvoiceEntity> save(MultipartFile file);
    PagedModel<VehicleInvoiceResponse> findByApartmentId(String apartmentId, Integer page, Integer limit);
}
