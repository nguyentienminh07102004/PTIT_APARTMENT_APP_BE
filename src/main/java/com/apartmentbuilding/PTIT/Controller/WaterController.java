package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Response.WaterInvoiceResponse;
import com.apartmentbuilding.PTIT.Service.ElectricWater.IElectricWaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/waters")
public class WaterController {
    private final IElectricWaterService electricWaterService;

    @PostMapping(value = "/")
    public ResponseEntity<List<WaterInvoiceResponse>> uploadFileExcel(@RequestPart MultipartFile file) {
        List<WaterInvoiceResponse> waterInvoiceResponses = electricWaterService.saveAllWaterInvoice(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(waterInvoiceResponses);
    }

    @GetMapping(value = "/apartment/{apartmentId}")
    public ResponseEntity<PagedModel<WaterInvoiceResponse>> findWaterBillByApartmentId(@PathVariable String apartmentId,
                                                                  @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                  @RequestParam(required = false, defaultValue = "10") Integer limit) {
        PagedModel<WaterInvoiceResponse> result = electricWaterService.findWaterByApartmentId(apartmentId, page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
