package com.apartmentbuilding.PTIT.Service.Floor;

import com.apartmentbuilding.PTIT.Model.Entity.FloorEntity;

import java.util.List;

public interface IFloorService {
    FloorEntity findById(String id);
    FloorEntity findByName(String name);
    FloorEntity save(FloorEntity floor);
    List<FloorEntity> saveAll(List<FloorEntity> floors);
    Long countOfFloors();
}
