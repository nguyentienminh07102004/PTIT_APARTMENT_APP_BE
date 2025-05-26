package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Request.Vehicle.VehicleCreate;
import com.apartmentbuilding.PTIT.DTO.Response.Vehicle.VehicleResponse;
import com.apartmentbuilding.PTIT.Mapper.Vehicle.VehicleMapper;
import com.apartmentbuilding.PTIT.Service.Vehicle.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/vehicles")
public class VehicleController {
    private final IVehicleService vehicleService;
    private final VehicleMapper vehicleMapper;

    @PostMapping()
    public ResponseEntity<VehicleResponse> createVehicle(@RequestBody VehicleCreate vehicleCreate) {
        VehicleResponse vehicleResponse = this.vehicleMapper.toResponse(this.vehicleService.create(vehicleCreate));
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleResponse);
    }

    public ResponseEntity<PagedModel<VehicleResponse>> findMyVehicles(Integer page, Integer limit) {
        return null;
    }

    @DeleteMapping(value = "/{ids}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable List<String> ids) {
        this.vehicleService.delete(ids);
        return ResponseEntity.status(200).build();
    }
}
