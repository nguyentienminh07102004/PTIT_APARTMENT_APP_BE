package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Domains.JwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IJwtRepository extends JpaRepository<JwtEntity, String> {
    void deleteAllByExpiryBefore(Date date);
}
