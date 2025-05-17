package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Response.MonthlyInvoiceResponse;
import com.apartmentbuilding.PTIT.Service.MonthlyInvoice.IMonthlyInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/monthly-invoice")
public class MonthlyInvoiceController {
    private final IMonthlyInvoiceService monthlyInvoiceService;

    @GetMapping(value = "/apartments/{apartmentName}")
    public ResponseEntity<PagedModel<MonthlyInvoiceResponse>> findMonthlyInvoiceByApartmentId(@PathVariable String apartmentName,
                                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                       @RequestParam(required = false, defaultValue = "10") Integer limit) {
        PagedModel<MonthlyInvoiceResponse> monthInvoices = this.monthlyInvoiceService.findByApartment_Name(apartmentName, page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(monthInvoices);
    }

    @GetMapping(value = "/my-invoices")
    public ResponseEntity<PagedModel<MonthlyInvoiceResponse>> findMyInvoice(@RequestParam(required = false) Integer page,
                                                                            @RequestParam(required = false) Integer limit) {
        PagedModel<MonthlyInvoiceResponse> invoiceResponses = this.monthlyInvoiceService.findMyInvoice(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(invoiceResponses);
    }
}
