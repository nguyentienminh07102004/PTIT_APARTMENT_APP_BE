package com.apartmentbuilding.PTIT.Service.ServiceType;

import com.apartmentbuilding.PTIT.Model.Entity.ServiceTypeEntity;

public interface IServiceType {
    ServiceTypeEntity findByName(String name);
}
