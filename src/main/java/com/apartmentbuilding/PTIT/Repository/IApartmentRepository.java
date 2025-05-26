package com.apartmentbuilding.PTIT.Repository;

import com.apartmentbuilding.PTIT.Model.Entity.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@Repository
public interface IApartmentRepository extends JpaRepository<ApartmentEntity, String> {
    List<ApartmentEntity> findByUser_Email(String userEmail);

    Optional<ApartmentEntity> findByName(String name);

    @Query(value = """
            select distinct u.email from apartments a 
                            join users u on u.email = a.useremail
                        where a.useremail is not null
            """, nativeQuery = true)
    List<String> findAllNames();

    @Query(value = """
            select distinct a.name from apartments a where a.useremail = :email
            """, nativeQuery = true)
    List<String> findByApartmentNameByUserEmail(String email);
}
