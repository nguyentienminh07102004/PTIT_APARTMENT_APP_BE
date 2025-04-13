package com.apartmentbuilding.PTIT.Service.Building;

import com.apartmentbuilding.PTIT.Model.Entity.BuildingEntity;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Repository.IBuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IBuildingServiceImpl implements IBuildingService {
    private final IBuildingRepository buildingRepository;

    @Override
    @Transactional(readOnly = true)
    public BuildingEntity findById(String id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.BUILDING_NOT_FOUND));
    }

    @Override
    @Transactional
    public BuildingEntity save(BuildingEntity building) {
        return this.buildingRepository.save(building);
    }

    @Override
    public boolean existsByName(String name) {
        return this.buildingRepository.existsByName(name);
    }

    @Override
    public boolean isEmpty() {
        return this.buildingRepository.count() == 0;
    }
}
