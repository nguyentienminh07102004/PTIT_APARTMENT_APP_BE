package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Request.Building.BuildingSearchRequest;
import com.apartmentbuilding.PTIT.DTO.Request.Building.BuildingUpdateRequest;
import com.apartmentbuilding.PTIT.DTO.Response.BuildingResponse;
import com.apartmentbuilding.PTIT.Service.Building.IBuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/buildings")
public class BuildingController {
    private final IBuildingService buildingService;

    @GetMapping()
    public ResponseEntity<PagedModel<BuildingResponse>> findAllBuilding(@ModelAttribute BuildingSearchRequest buildingSearchRequest) {
        PagedModel<BuildingResponse> buildingResponsePagedModel = this.buildingService.findAll(buildingSearchRequest);
        return ResponseEntity.status(HttpStatus.OK).body(buildingResponsePagedModel);
    }

    @PutMapping()
    public ResponseEntity<BuildingResponse> updateBuildings(@RequestBody BuildingUpdateRequest request) {
        BuildingResponse buildingResponse = this.buildingService.updateBuilding(request);
        return ResponseEntity.status(HttpStatus.OK).body(buildingResponse);
    }
}
