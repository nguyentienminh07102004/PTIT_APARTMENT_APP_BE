package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.ServiceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IServiceTypeRepository extends JpaRepository<ServiceTypeEntity, String> {
    Optional<ServiceTypeEntity> findByName(String name);
}
