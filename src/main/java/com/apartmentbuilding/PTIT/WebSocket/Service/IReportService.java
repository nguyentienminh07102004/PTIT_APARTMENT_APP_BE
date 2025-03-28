package com.apartmentbuilding.PTIT.WebSocket.Service;

import com.apartmentbuilding.PTIT.WebSocket.Domains.ReportDocument;
import org.springframework.data.web.PagedModel;
import org.springframework.web.multipart.MultipartFile;

public interface IReportService {
    ReportDocument create(MultipartFile file);
    ReportDocument findReportById(String id);
    PagedModel<ReportDocument> findReport(Integer page, Integer limit);
}
