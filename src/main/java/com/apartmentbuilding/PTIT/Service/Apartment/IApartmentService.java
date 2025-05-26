package com.apartmentbuilding.PTIT.Service.Apartment;

import com.apartmentbuilding.PTIT.DTO.Request.Apartment.ApartmentSearchRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ApartmentResponse;
import com.apartmentbuilding.PTIT.DTO.Request.Apartment.ApartmentRequest;
import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import org.springframework.data.web.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IApartmentService {
    ApartmentEntity findById(String id);

    ApartmentEntity findByName(String name);

    ApartmentResponse save(ApartmentRequest apartmentRequest);

    List<ApartmentResponse> saveFromExcel(MultipartFile file);

    PagedModel<ApartmentResponse> findAll(ApartmentSearchRequest apartmentSearchRequest);

    List<ApartmentResponse> findMyApartment();

    List<String> getAllApartmentNames();

    List<String> getAllApartmentNameByUserEmail(String email);
}
