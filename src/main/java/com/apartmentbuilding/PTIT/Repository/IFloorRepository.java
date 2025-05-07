package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.FloorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFloorRepository extends JpaRepository<FloorEntity, String> {
    Optional<FloorEntity> findByFloorName(String floorName);
}
