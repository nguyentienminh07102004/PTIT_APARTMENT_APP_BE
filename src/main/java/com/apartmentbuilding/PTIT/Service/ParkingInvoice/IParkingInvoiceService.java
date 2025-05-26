package com.apartmentbuilding.PTIT.Service.ParkingInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.ParkingInvoice.ParkingInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Request.ParkingInvoice.ParkingInvoiceSearch;
import com.apartmentbuilding.PTIT.DTO.Response.ParkingInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ParkingInvoiceEntity;
import org.springframework.data.web.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IParkingInvoiceService {
    ParkingInvoiceEntity save(ParkingInvoiceRequest request);
    List<ParkingInvoiceEntity> save(MultipartFile file);
    PagedModel<ParkingInvoiceResponse> findByApartmentId(String apartmentId, Integer page, Integer limit);

    PagedModel<ParkingInvoiceResponse> findParkingInvoice(ParkingInvoiceSearch parkingInvoiceSearch);
}
