package com.apartmentbuilding.PTIT.DTO.Request.User;

import com.apartmentbuilding.PTIT.Common.Validate.EmailHasExists.EmailHasExists;
import com.apartmentbuilding.PTIT.Common.Validate.IdentityNumberHasExists.IdentityNumberHasExists;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UserRegister {
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "EMAIL_INVALID")
    @EmailHasExists
//    @JsonProperty(value = "Email")
    private String email;
    @Size(min = 8, message = "PASSWORD_LENGTH_NOT_CORRECT")
    private String password;
    private String fullName;
    private Date dateOfBirth;
    @NotNull(message = "IDENTITY_NUMBER_NOT_NULL_OR_EMPTY")
    @NotBlank(message = "IDENTITY_NUMBER_NOT_NULL_OR_EMPTY")
    @IdentityNumberHasExists
    private String identityNumber;
    private String phoneNumber;
    @Size(min = 8, message = "PASSWORD_LENGTH_NOT_CORRECT")
    private String confirmPassword;
    private String roleCode;
}
