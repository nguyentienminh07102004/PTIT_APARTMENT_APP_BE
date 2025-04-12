package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.VehicleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IVehicleTypeRepository extends JpaRepository<VehicleTypeEntity, String> {
    Optional<VehicleTypeEntity> findByName(String name);
}
