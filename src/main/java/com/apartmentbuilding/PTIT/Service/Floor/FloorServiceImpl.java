package com.apartmentbuilding.PTIT.Service.Floor;

import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.Model.Entity.FloorEntity;
import com.apartmentbuilding.PTIT.Repository.IFloorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
