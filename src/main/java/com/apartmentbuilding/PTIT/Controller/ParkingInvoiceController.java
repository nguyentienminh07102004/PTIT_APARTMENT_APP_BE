package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Response.ParkingInvoiceResponse;
import com.apartmentbuilding.PTIT.Model.Entity.ParkingInvoiceEntity;
import com.apartmentbuilding.PTIT.Service.ParkingInvoice.IParkingInvoiceService;
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
@RequestMapping(value = "/vehicle-invoices")
public class ParkingInvoiceController {
    private final IParkingInvoiceService parkingInvoiceService;

    @PostMapping()
    public ResponseEntity<List<ParkingInvoiceEntity>> save(@RequestParam MultipartFile file) {
        List<ParkingInvoiceEntity> vehicleInvoiceEntities = this.parkingInvoiceService.save(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleInvoiceEntities);
    }

    @GetMapping(value = "/apartments/{apartmentId}")
    public ResponseEntity<PagedModel<ParkingInvoiceResponse>> findByApartmentId(@PathVariable String apartmentId,
                                                                                @RequestParam(required = false) Integer page,
                                                                                @RequestParam(required = false) Integer limit) {
        PagedModel<ParkingInvoiceResponse> result = this.parkingInvoiceService.findByApartmentId(apartmentId, page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
