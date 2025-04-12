package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Request.Apartment.ApartmentSearchRequest;
import com.apartmentbuilding.PTIT.DTO.Response.APIResponse;
import com.apartmentbuilding.PTIT.DTO.Response.ApartmentResponse;
import com.apartmentbuilding.PTIT.DTO.Request.Apartment.ApartmentRequest;
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

    @PostMapping(value = "/")
    public ResponseEntity<APIResponse> save(@Valid @RequestBody ApartmentRequest request) {
        ApartmentResponse apartmentResponse = this.apartmentService.save(request);
        APIResponse response = APIResponse.builder()
                .message("SUCCESS")
                .code(HttpStatus.CREATED.value())
                .data(apartmentResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "/read-excel")
    public ResponseEntity<APIResponse> saveFromFileExcel(@RequestPart MultipartFile file) {
        List<ApartmentResponse> apartmentResponse = this.apartmentService.saveFromExcel(file);
        APIResponse response = APIResponse.builder()
                .message("SUCCESS")
                .code(HttpStatus.CREATED.value())
                .data(apartmentResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/")
    public ResponseEntity<APIResponse> findAllApartments(@ModelAttribute ApartmentSearchRequest request) {
        PagedModel<ApartmentResponse> apartmentResponsePagedModel = this.apartmentService.findAll(request);
        APIResponse response = APIResponse.builder()
                .message("SUCCESS")
                .code(HttpStatus.OK.value())
                .data(apartmentResponsePagedModel)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
