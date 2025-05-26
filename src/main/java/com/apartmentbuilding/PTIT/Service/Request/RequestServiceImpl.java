package com.apartmentbuilding.PTIT.Service.Request;

import com.apartmentbuilding.PTIT.Common.Enums.RequestStatus;
import com.apartmentbuilding.PTIT.DTO.Request.Request.RequestRequest;
import com.apartmentbuilding.PTIT.DTO.Response.Request.RequestResponse;
import com.apartmentbuilding.PTIT.Model.Entity.RequestEntity;
import com.apartmentbuilding.PTIT.Repository.IRequestRepository;
import com.apartmentbuilding.PTIT.Service.Apartment.IApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements IRequestService {
    private final IRequestRepository requestRepository;
    private final IApartmentService apartmentService;

    @Override
    public RequestResponse createRequest(RequestRequest requestRequest) {
        RequestEntity request = new RequestEntity();
        request.setTitle(requestRequest.getTitle());
        request.setContent(requestRequest.getContent());
        request.setType(requestRequest.getType());
        request.setApartment(this.apartmentService.findByName(requestRequest.getApartmentName()));
        request.setStatus(RequestStatus.PENDING);
        requestRepository.save(request);
        return RequestResponse.builder()
                .id(request.getId())
                .title(request.getTitle())
                .content(request.getContent())
                .createdBy(request.getCreatedBy())
                .createdDate(request.getCreatedDate())
                .status(request.getStatus())
                .apartmentName(request.getApartment().getName())
                .build();
    }
}
