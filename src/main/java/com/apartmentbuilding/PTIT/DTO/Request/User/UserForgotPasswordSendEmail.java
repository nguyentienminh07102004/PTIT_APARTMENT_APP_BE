package com.apartmentbuilding.PTIT.DTO.Request.User;

import jakarta.validation.constraints.Email;
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
public class UserForgotPasswordSendEmail {
    @Email(message = "EMAIL_INVALID")
    private String email;
}
