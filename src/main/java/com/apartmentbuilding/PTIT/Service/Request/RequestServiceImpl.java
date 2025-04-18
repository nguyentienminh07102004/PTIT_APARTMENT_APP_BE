package com.apartmentbuilding.PTIT.Service.Request;

import com.apartmentbuilding.PTIT.Common.Enum.RequestStatus;
import com.apartmentbuilding.PTIT.DTO.Request.Request.RequestRequest;
import com.apartmentbuilding.PTIT.DTO.Request.Request.RequestSearchRequest;
import com.apartmentbuilding.PTIT.Mapper.Report.IRequestMapper;
import com.apartmentbuilding.PTIT.Model.Document.RequestDocument;
import com.apartmentbuilding.PTIT.Repository.IRequestRepository;
import com.apartmentbuilding.PTIT.Utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements IRequestService {
    private final IRequestRepository reportRepository;
    private final IRequestMapper requestMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public RequestDocument create(RequestRequest request) {
        RequestDocument reportDocument = this.requestMapper.requestToDocument(request);
        reportDocument.setEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        reportDocument.setStatus(RequestStatus.PENDING);
        return this.reportRepository.save(reportDocument);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<RequestDocument> findAll(RequestSearchRequest request) {
        Page<RequestDocument> requestDocuments = this.reportRepository.findAll(PaginationUtils.pagination(request.getPage(), request.getLimit()));
        return new PagedModel<>(requestDocuments);
    }

    @Override
    @Transactional
    public void notifyRequestToAdmin(String destination, RequestDocument requestDocument) {
        this.messagingTemplate.convertAndSend(destination, requestDocument);
    }
}
