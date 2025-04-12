package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Response.APIResponse;
import com.apartmentbuilding.PTIT.DTO.Response.VehicleInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleInvoiceEntity;
import com.apartmentbuilding.PTIT.Service.VehicleInvoice.IVehicleInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/vehicle-invoice")
public class VehicleInvoiceController {
    private final IVehicleInvoiceService vehicleInvoiceService;

    @PostMapping(value = "/")
    public ResponseEntity<APIResponse> save(@RequestParam MultipartFile file) {
        List<VehicleInvoiceEntity> vehicleInvoiceEntities = vehicleInvoiceService.save(file);
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("SUCCESS")
                .data(vehicleInvoiceEntities)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/apartments/{apartmentId}")
    public ResponseEntity<APIResponse> findByApartmentId(@PathVariable String apartmentId,
                                                         @RequestParam(required = false) Integer page,
                                                         @RequestParam(required = false) Integer limit) {
        PagedModel<VehicleInvoiceResponse> result = vehicleInvoiceService.findByApartmentId(apartmentId, page, limit);
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.OK.value())
                .message("SUCCESS")
                .data(result)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
