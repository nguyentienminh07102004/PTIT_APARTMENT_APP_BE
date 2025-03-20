package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Reponse.APIResponse;
import com.apartmentbuilding.PTIT.DTO.Reponse.ElectricInvoiceResponse;
import com.apartmentbuilding.PTIT.Service.ElectricWater.IElectricWaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/${api_prefix}/electrics")
public class ElectricController {
    private final IElectricWaterService electricWaterService;

    @PostMapping(value = "/")
    public ResponseEntity<APIResponse> uploadFileExcel(@RequestPart MultipartFile file) {
        List<ElectricInvoiceResponse> result = electricWaterService.saveAllElectricInvoice(file);
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("SUCCESS")
                .data(result)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/apartment/{apartmentId}")
    public ResponseEntity<APIResponse> findElectricBillByApartmentId(@PathVariable String apartmentId) {
        List<ElectricInvoiceResponse> result = electricWaterService.findByApartmentId(apartmentId);
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.OK.value())
                .message("SUCCESS")
                .data(result)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
