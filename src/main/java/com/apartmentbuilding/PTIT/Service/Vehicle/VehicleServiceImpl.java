package com.apartmentbuilding.PTIT.Service.Vehicle;

import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.ExceptionVariable;
import com.apartmentbuilding.PTIT.Model.Entity.VehicleEntity;
import com.apartmentbuilding.PTIT.Repository.IVehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements IVehicleService {
    private final IVehicleRepository vehicleRepository;

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
}
