package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@Repository
public interface IApartmentRepository extends JpaRepository<ApartmentEntity, String> {
    List<ApartmentEntity> findByUser_Email(String userEmail);

    Optional<ApartmentEntity> findByName(String name);
}
