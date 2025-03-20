package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Domains.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, String> {
    Optional<RoleEntity> findByCode(String code);
    boolean existsByCode(String code);
}
