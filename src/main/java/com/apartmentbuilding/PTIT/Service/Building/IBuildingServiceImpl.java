package com.apartmentbuilding.PTIT.Service.Building;

import com.apartmentbuilding.PTIT.Domains.BuildingEntity;
import com.apartmentbuilding.PTIT.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.ExceptionAdvice.ExceptionVariable;
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
}
