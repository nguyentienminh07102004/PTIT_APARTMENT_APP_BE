package com.apartmentbuilding.PTIT.Mapper.MonthlyInvoice;

import com.apartmentbuilding.PTIT.DTO.Response.MonthlyInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.MonthlyInvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IMonthlyInvoiceMapper {
    @Mapping(target = "vehicleInvoices", ignore = true)
    MonthlyInvoiceResponse entityToResponse(MonthlyInvoiceEntity entity);
}