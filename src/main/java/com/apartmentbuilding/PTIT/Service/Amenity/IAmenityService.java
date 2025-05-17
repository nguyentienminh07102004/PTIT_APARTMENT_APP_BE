package com.apartmentbuilding.PTIT.Service.Amenity;

import com.apartmentbuilding.PTIT.DTO.Response.Amenity.AmenityResponse;
import org.springframework.data.web.PagedModel;

public interface IAmenityService {
    PagedModel<AmenityResponse> findAllAmenity(Integer page, Integer limit);
}
