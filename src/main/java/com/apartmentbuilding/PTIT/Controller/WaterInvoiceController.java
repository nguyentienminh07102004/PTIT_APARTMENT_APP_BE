package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Request.WaterInvoice.WaterInvoiceSearchRequest;
import com.apartmentbuilding.PTIT.DTO.Request.WaterInvoice.WaterInvoiceUpdate;
import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.Service.WaterInvoice.IWaterInvoiceService;
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
@RequestMapping(value = "/waters")
public class WaterInvoiceController {
    private final IWaterInvoiceService waterService;

    @PostMapping(value = "/read-excel")
    public ResponseEntity<List<WaterInvoiceResponse>> uploadFileExcel(@RequestPart MultipartFile file) {
        List<WaterInvoiceResponse> waterInvoiceResponses = this.waterService.saveFromExcel(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(waterInvoiceResponses);
    }

    @GetMapping()
    public ResponseEntity<PagedModel<WaterInvoiceResponse>> findWaterInvoice(@ModelAttribute WaterInvoiceSearchRequest waterInvoiceSearchRequest) {
        PagedModel<WaterInvoiceResponse> result = this.waterService.findWaterInvoice(waterInvoiceSearchRequest);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PutMapping()
    public ResponseEntity<WaterInvoiceResponse> updateInvoice(@Valid @RequestBody WaterInvoiceUpdate invoiceUpdate) {
        WaterInvoiceResponse result = this.waterService.updateWaterInvoice(invoiceUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping(value = "/my-invoices")
    public ResponseEntity<PagedModel<WaterInvoiceResponse>> findMyWaterInvoices(@RequestParam(required = false) Integer page,
                                                                                @RequestParam(required = false) Integer limit,
                                                                                @RequestParam(required = false) String search) {
        PagedModel<WaterInvoiceResponse> waterInvoiceResponses = this.waterService.findMyWaterInvoice(search, page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(waterInvoiceResponses);
    }
}
