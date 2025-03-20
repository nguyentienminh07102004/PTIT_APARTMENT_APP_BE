package com.apartmentbuilding.PTIT.DTO.Reponse;

import com.apartmentbuilding.PTIT.Domains.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String identityNumber;
    private Date dateOfBirth;
    private RoleEntity role;
}
