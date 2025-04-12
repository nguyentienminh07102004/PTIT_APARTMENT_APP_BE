package com.apartmentbuilding.PTIT.DTO.Request.User;

import com.apartmentbuilding.PTIT.Common.Validate.AccountLoginInOtherDevice.AccountLoginInOtherDevice;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequest {
    @AccountLoginInOtherDevice
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "EMAIL_INVALID")
    private String email;
    @Size(min = 8)
    private String password;
    private Boolean isSocial;
}
