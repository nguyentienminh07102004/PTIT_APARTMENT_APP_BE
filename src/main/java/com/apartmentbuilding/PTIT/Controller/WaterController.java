package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Reponse.APIResponse;
import com.apartmentbuilding.PTIT.DTO.Reponse.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.DTO.Reponse.WaterInvoiceResponse;
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
@RequestMapping(value = "/${api_prefix}/waters")
public class WaterController {
    private final IElectricWaterService electricWaterService;

    @PostMapping(value = "/")
    public ResponseEntity<APIResponse> uploadFileExcel(@RequestPart MultipartFile file) {
        List<WaterInvoiceResponse> waterInvoiceResponses = electricWaterService.saveAllWaterInvoice(file);
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("SUCCESS")
                .data(waterInvoiceResponses)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/apartment/{apartmentId}")
    public ResponseEntity<APIResponse> findWaterBillByApartmentId(@PathVariable String apartmentId,
                                                                  @RequestParam(required = false, defaultValue = "1") Integer page,
                                                                  @RequestParam(required = false, defaultValue = "10") Integer limit) {
        PagedModel<WaterInvoiceResponse> result = electricWaterService.findWaterByApartmentId(apartmentId, page, limit);
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.OK.value())
                .message("SUCCESS")
                .data(result)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
