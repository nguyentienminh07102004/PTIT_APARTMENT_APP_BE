package com.apartmentbuilding.PTIT.Service.VehicleType;

import com.apartmentbuilding.PTIT.Model.Entity.VehicleTypeEntity;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Repository.IVehicleTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VehicleTypeServiceImpl implements IVehicleTypeService {
    private final IVehicleTypeRepository vehicleTypeRepository;


    @Override
    @Transactional(readOnly = true)
    public VehicleTypeEntity findByName(String id) {
        return this.vehicleTypeRepository.findByName(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.VEHICLE_TYPE_NOT_FOUND));
    }
}
