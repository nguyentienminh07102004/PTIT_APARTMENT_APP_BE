package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IVehicleRepository extends JpaRepository<VehicleEntity, String> {
    Optional<VehicleEntity> findByLicensePlate(String licensePlate);
}
