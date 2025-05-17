package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.Service.Vehicle.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/vehicles")
public class VehicleController {
    private final IVehicleService vehicleService;
}
