package com.apartmentbuilding.PTIT.Service.Request;

import com.apartmentbuilding.PTIT.DTO.Request.Request.RequestRequest;
import com.apartmentbuilding.PTIT.DTO.Response.Request.RequestResponse;

public interface IRequestService {
    RequestResponse createRequest(RequestRequest requestRequest);
}
