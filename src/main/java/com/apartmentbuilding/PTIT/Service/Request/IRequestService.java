package com.apartmentbuilding.PTIT.Service.Request;

import com.apartmentbuilding.PTIT.DTO.Request.Request.RequestRequest;
import com.apartmentbuilding.PTIT.DTO.Request.Request.RequestSearchRequest;
import com.apartmentbuilding.PTIT.Model.Document.RequestDocument;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface IRequestService {
    RequestDocument create(RequestRequest request);
    PagedModel<RequestDocument> findAll(RequestSearchRequest request);
    void notifyRequestToAdmin(String destination, RequestDocument requestDocument);
}
