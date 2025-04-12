package com.apartmentbuilding.PTIT.Service.Report;

import com.apartmentbuilding.PTIT.DTO.Request.Report.ReportRequest;
import com.apartmentbuilding.PTIT.Model.Document.ReportDocument;
import com.apartmentbuilding.PTIT.Mapper.Report.IReportMapper;
import com.apartmentbuilding.PTIT.Repository.IReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService {
    private final IReportRepository reportRepository;
    private final IReportMapper reportMapper;

    @Override
    @Transactional
    public ReportDocument create(ReportRequest request, String email) {
        ReportDocument reportDocument = this.reportMapper.requestToDocument(request);
        reportDocument.setEmail(email);
        return this.reportRepository.save(reportDocument);
    }

    @Override
    public List<ReportDocument> findAll() {
        return this.reportRepository.findAll();
    }
}
