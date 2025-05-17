package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Request.ServiceInvoice.ServiceInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ServiceInvoiceResponse;
import com.apartmentbuilding.PTIT.Service.ServiceInvoice.IServiceInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/service-invoices")
public class ServiceInvoiceController {
    private final IServiceInvoiceService serviceInvoiceService;

    @GetMapping(value = "/all")
    public ResponseEntity<PagedModel<ServiceInvoiceResponse>> findAllServiceInvoices(@RequestParam(required = false) Integer page,
                                                                                     @RequestParam(required = false) Integer limit) {
        PagedModel<ServiceInvoiceResponse> res = this.serviceInvoiceService.findAllInvoices(page, limit);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping(value = "/my-invoices")
    public ResponseEntity<PagedModel<ServiceInvoiceResponse>> findMyInvoices(@RequestParam(required = false) Integer page,
                                                                             @RequestParam(required = false) Integer limit) {
        PagedModel<ServiceInvoiceResponse> res = this.serviceInvoiceService.findMyInvoices(page, limit);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ServiceInvoiceResponse> findServiceInvoiceById(@RequestBody ServiceInvoiceRequest request) {
        ServiceInvoiceResponse serviceInvoiceResponse = this.serviceInvoiceService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceInvoiceResponse);
    }
}
