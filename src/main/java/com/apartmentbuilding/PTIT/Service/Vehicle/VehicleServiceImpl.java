package com.apartmentbuilding.PTIT.Service.Vehicle;

import com.apartmentbuilding.PTIT.Common.Enums.VehicleStatus;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.apartmentbuilding.PTIT.DTO.Request.Vehicle.VehicleCreate;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleEntity;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleTypeEntity;
import com.apartmentbuilding.PTIT.Repository.IVehicleRepository;
import com.apartmentbuilding.PTIT.Service.Apartment.IApartmentService;
import com.apartmentbuilding.PTIT.Service.VehicleType.IVehicleTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehicleService {
    private final IVehicleRepository vehicleRepository;
    private final IVehicleTypeService vehicleTypeService;
    private final IApartmentService apartmentService;

    @Override
    @Transactional(readOnly = true)
    public VehicleEntity findById(String id) {
        return this.vehicleRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.VEHICLE_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleEntity findByLicensePlate(String licensePlate) {
        return this.vehicleRepository.findByLicensePlate(licensePlate)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.VEHICLE_NOT_FOUND));
    }

    @Override
    @Transactional
    public VehicleEntity create(VehicleCreate create) {
        Boolean isExists = this.vehicleRepository.existsByLicensePlate(create.getLicensePlate());
        if (isExists) {
            throw new DataInvalidException(ExceptionVariable.LICENSE_PLATE_HAS_EXISTS);
        }
        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setLicensePlate(create.getLicensePlate());
        vehicle.setStatus(VehicleStatus.ACTIVE);
        VehicleTypeEntity vehicleType = this.vehicleTypeService.findByName(create.getVehicleType());
        vehicle.setType(vehicleType);
        vehicle.setApartment(this.apartmentService.findByName(create.getApartmentName()));
        this.vehicleRepository.save(vehicle);
        return vehicle;
    }

    @Override
    @Transactional
    public void delete(List<String> ids) {
        for (String id : ids) {
            VehicleEntity vehicle = this.findById(id);
            if (!vehicle.getApartment().getUser().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
                throw new DataInvalidException(ExceptionVariable.VEHICLE_NOT_FOUND);
            }
            vehicle.setStatus(vehicle.getStatus().equals(VehicleStatus.ACTIVE) ? VehicleStatus.INACTIVE : VehicleStatus.ACTIVE);
            this.vehicleRepository.save(vehicle);
        }
    }
}
