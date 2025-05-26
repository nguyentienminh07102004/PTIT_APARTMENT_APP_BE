package com.apartmentbuilding.PTIT.Service.Floor;

import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.Model.Entity.FloorEntity;
import com.apartmentbuilding.PTIT.Repository.IFloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FloorServiceImpl implements IFloorService {
    private final IFloorRepository floorRepository;

    @Override
    @Transactional(readOnly = true)
    public FloorEntity findById(String id) {
        return this.floorRepository.findById(id)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.FLOOR_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public FloorEntity findByName(String name) {
        return this.floorRepository.findByFloorName(name)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.FLOOR_NOT_FOUND));
    }

    @Override
    @Transactional
    public FloorEntity save(FloorEntity floor) {
        return this.floorRepository.save(floor);
    }

    @Override
    @Transactional
    public List<FloorEntity> saveAll(List<FloorEntity> floors) {
        return this.floorRepository.saveAll(floors);
    }

    @Override
    public Long countOfFloors() {
        return this.floorRepository.count();
    }
}
