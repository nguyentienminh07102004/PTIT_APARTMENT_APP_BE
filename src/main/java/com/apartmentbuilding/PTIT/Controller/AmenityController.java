package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Response.Amenity.AmenityResponse;
import com.apartmentbuilding.PTIT.Service.Amenity.IAmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/amenities")
public class AmenityController {
    private final IAmenityService amenityService;

    @GetMapping()
    public ResponseEntity<PagedModel<AmenityResponse>> findAllAmenities(@RequestParam(required = false) Integer page,
                                                                        @RequestParam(required = false) Integer limit) {
        return ResponseEntity.status(HttpStatus.OK).body(this.amenityService.findAllAmenity(page, limit));
    }
}
