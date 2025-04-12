package com.apartmentbuilding.PTIT.Mapper.Report;

import com.apartmentbuilding.PTIT.DTO.Request.Report.ReportRequest;
import com.apartmentbuilding.PTIT.Model.Document.ReportDocument;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IReportMapper {
    ReportDocument requestToDocument(ReportRequest reportRequest);
}