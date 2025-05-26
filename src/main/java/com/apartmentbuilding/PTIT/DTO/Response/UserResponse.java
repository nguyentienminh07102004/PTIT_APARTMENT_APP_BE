package com.apartmentbuilding.PTIT.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse implements Serializable {
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String identityNumber;
    private Date dateOfBirth;
    private RoleResponse role;

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UserResponse && ((UserResponse) obj).id.equals(this.id) && ((UserResponse) obj).email.equals(this.email);
    }
}
