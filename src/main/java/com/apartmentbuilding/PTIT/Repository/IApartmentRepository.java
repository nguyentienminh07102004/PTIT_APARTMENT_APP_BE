package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Domains.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IApartmentRepository extends JpaRepository<ApartmentEntity, String> {
}
