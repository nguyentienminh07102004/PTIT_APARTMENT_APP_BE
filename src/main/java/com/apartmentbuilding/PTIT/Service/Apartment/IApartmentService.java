package com.apartmentbuilding.PTIT.Service.Apartment;

import com.apartmentbuilding.PTIT.DTO.Reponse.ApartmentResponse;
import com.apartmentbuilding.PTIT.DTO.Request.Apartment.ApartmentRequest;
import com.apartmentbuilding.PTIT.Domains.ApartmentEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IApartmentService {
    ApartmentEntity findById(String id);

    ApartmentResponse save(ApartmentRequest apartmentRequest);

    List<ApartmentResponse> saveFromExcel(MultipartFile file);
}
