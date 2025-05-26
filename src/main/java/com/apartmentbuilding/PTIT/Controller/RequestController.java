package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.Service.Request.IRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/requests")
public class RequestController {
    private final IRequestService requestService;

//    public Re
}
