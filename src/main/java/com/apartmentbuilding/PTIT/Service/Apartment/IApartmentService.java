package com.apartmentbuilding.PTIT.Service.Apartment;

import com.apartmentbuilding.PTIT.Domains.ApartmentEntity;

public interface IApartmentService {
    ApartmentEntity findById(String id);
}
