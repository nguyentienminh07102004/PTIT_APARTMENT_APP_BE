package com.apartmentbuilding.PTIT.WebSocket.Service;

import com.apartmentbuilding.PTIT.WebSocket.Domains.ReportDocument;
import com.apartmentbuilding.PTIT.WebSocket.Repository.IReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService {
    private final IReportRepository reportRepository;
    private final GridFsTemplate gridFsOperations;

    @Override
    @Transactional
    public ReportDocument create(MultipartFile file) {
        try {
            gridFsOperations.store(file.getInputStream(), Objects.requireNonNull(file.getOriginalFilename()));
            return null;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ReportDocument findReportById(String id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("NOT FOUND %s".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<ReportDocument> findReport(Integer page, Integer limit) {
        Page<ReportDocument> pagedReport = reportRepository.findAll(PageRequest.of(page, limit));
        return new PagedModel<>(pagedReport);
    }
}
