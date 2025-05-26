package com.apartmentbuilding.PTIT.Mapper.Request;

import com.apartmentbuilding.PTIT.DTO.Request.Request.RequestRequest;
import com.apartmentbuilding.PTIT.Model.Entity.RequestEntity;

public interface IRequestMapper {
    RequestEntity toEntity(RequestRequest request);
}
