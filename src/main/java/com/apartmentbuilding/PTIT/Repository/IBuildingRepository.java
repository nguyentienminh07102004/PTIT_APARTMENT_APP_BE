package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Domains.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBuildingRepository extends JpaRepository<BuildingEntity, String> {

}
