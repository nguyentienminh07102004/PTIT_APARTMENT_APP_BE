package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Request.Request.RequestRequest;
import com.apartmentbuilding.PTIT.DTO.Request.Request.RequestSearchRequest;
import com.apartmentbuilding.PTIT.Model.Document.RequestDocument;
import com.apartmentbuilding.PTIT.Service.Request.IRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/requests")
public class RequestController {
    private final IRequestService requestService;

    @PostMapping(value = "/send-request")
    @SendTo(value = "/topic/requests")
    public RequestDocument sendRequest(@RequestBody RequestRequest report) {
        RequestDocument document = this.requestService.create(report);
        this.requestService.notifyRequestToAdmin("/topic/requests", document);
        return document;
    }

    @GetMapping()
    public ResponseEntity<PagedModel<RequestDocument>> sendDocument(@ModelAttribute RequestSearchRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(this.requestService.findAll(request));
    }
}
