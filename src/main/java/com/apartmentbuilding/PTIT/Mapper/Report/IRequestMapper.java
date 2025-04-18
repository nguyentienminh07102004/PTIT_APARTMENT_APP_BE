package com.apartmentbuilding.PTIT.Mapper.Report;

import com.apartmentbuilding.PTIT.DTO.Request.Request.RequestRequest;
import com.apartmentbuilding.PTIT.Model.Document.RequestDocument;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRequestMapper {
    RequestDocument requestToDocument(RequestRequest reportRequest);
}