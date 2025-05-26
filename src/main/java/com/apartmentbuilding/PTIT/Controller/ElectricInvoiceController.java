package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceRequest;
import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceSearch;
import com.apartmentbuilding.PTIT.DTO.Request.ElectricInvoice.ElectricInvoiceUpdate;
import com.apartmentbuilding.PTIT.DTO.Response.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.Service.ElectricInvoice.IElectricInvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/electrics")
public class ElectricInvoiceController {
    private final IElectricInvoiceService electricService;

    @PostMapping(value = "/read-excel")
    public ResponseEntity<List<ElectricInvoiceResponse>> uploadFileExcel(@RequestPart MultipartFile file) {
        List<ElectricInvoiceResponse> result = this.electricService.saveAllElectricInvoice(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping()
    public ResponseEntity<ElectricInvoiceResponse> createInvoice(@Valid @RequestBody ElectricInvoiceRequest invoiceRequest) {
        ElectricInvoiceResponse response = this.electricService.save(invoiceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public ResponseEntity<ElectricInvoiceResponse> updateInvoice(@Valid @RequestBody ElectricInvoiceUpdate invoiceRequest) {
        ElectricInvoiceResponse invoiceResponse = this.electricService.updateInvoice(invoiceRequest);
        return ResponseEntity.status(HttpStatus.OK).body(invoiceResponse);
    }

    @GetMapping(value = "/my-invoices")
    public ResponseEntity<PagedModel<ElectricInvoiceResponse>> findElectricInvoice(@RequestParam(required = false) String search,
                                                                                   @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                                   @RequestParam(required = false, defaultValue = "10") Integer limit) {
        PagedModel<ElectricInvoiceResponse> result = this.electricService.findElectricInvoice(search, page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping()
    public ResponseEntity<PagedModel<ElectricInvoiceResponse>> findElectricInvoice(@ModelAttribute ElectricInvoiceSearch search) {
        PagedModel<ElectricInvoiceResponse> result = this.electricService.findElectricInvoice(search);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
