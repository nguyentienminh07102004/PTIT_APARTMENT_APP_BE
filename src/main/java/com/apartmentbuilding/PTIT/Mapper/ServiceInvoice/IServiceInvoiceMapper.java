package com.apartmentbuilding.PTIT.Mapper.ServiceInvoice;

import com.apartmentbuilding.PTIT.DTO.Response.ServiceInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ServiceInvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IServiceInvoiceMapper {
    ServiceInvoiceResponse toResponse(ServiceInvoiceEntity entity);
}
