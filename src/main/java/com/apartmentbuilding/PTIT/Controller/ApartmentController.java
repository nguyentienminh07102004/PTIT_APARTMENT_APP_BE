package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Request.Apartment.ApartmentRequest;
import com.apartmentbuilding.PTIT.DTO.Request.Apartment.ApartmentSearchRequest;
import com.apartmentbuilding.PTIT.DTO.Response.ApartmentResponse;
import com.apartmentbuilding.PTIT.Service.Apartment.IApartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/apartments")
public class ApartmentController {
    private final IApartmentService apartmentService;

    @PostMapping()
    public ResponseEntity<ApartmentResponse> save(@Valid @RequestBody ApartmentRequest request) {
        ApartmentResponse apartmentResponse = this.apartmentService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(apartmentResponse);
    }

    @PostMapping(value = "/read-excel")
    public ResponseEntity<List<ApartmentResponse>> saveFromFileExcel(@RequestPart MultipartFile file) {
        List<ApartmentResponse> apartmentResponse = this.apartmentService.saveFromExcel(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(apartmentResponse);
    }

    @GetMapping()
    public ResponseEntity<PagedModel<ApartmentResponse>> findAllApartments(@ModelAttribute ApartmentSearchRequest request) {
        PagedModel<ApartmentResponse> apartmentResponsePagedModel = this.apartmentService.findAll(request);
        return ResponseEntity.status(HttpStatus.OK).body(apartmentResponsePagedModel);
    }

    @GetMapping(value = "/my-apartments")
    public ResponseEntity<List<ApartmentResponse>> findMyApartments() {
        List<ApartmentResponse> myApartments = this.apartmentService.findMyApartment();
        return ResponseEntity.status(HttpStatus.OK).body(myApartments);
    }

    @GetMapping(value = "/apartment-name")
    public ResponseEntity<List<String>> getAllApartmentNames() {
        List<String> apartmentNames = this.apartmentService.getAllApartmentNames();
        return ResponseEntity.status(HttpStatus.OK).body(apartmentNames);
    }
}
