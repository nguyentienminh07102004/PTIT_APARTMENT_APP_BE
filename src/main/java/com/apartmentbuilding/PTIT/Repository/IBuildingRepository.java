package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBuildingRepository extends JpaRepository<BuildingEntity, String> {

    boolean existsByName(String name);
}
