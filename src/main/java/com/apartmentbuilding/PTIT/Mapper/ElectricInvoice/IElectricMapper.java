package com.apartmentbuilding.PTIT.Mapper.ElectricInvoice;

import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceUpdate;
import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ElectricInvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IElectricMapper {
    ElectricInvoiceEntity requestToEntity(ElectricInvoiceRequest request);
    ElectricInvoiceEntity toEntity(ElectricInvoiceUpdate invoiceUpdate);
    ElectricInvoiceResponse entityToResponse(ElectricInvoiceEntity entity);
}
