package com.apartmentbuilding.PTIT.Service.Report;

import com.apartmentbuilding.PTIT.DTO.Request.Report.ReportRequest;
import com.apartmentbuilding.PTIT.Model.Document.ReportDocument;

import java.util.List;

public interface IReportService {
    ReportDocument create(ReportRequest request, String email);
    List<ReportDocument> findAll();
}
