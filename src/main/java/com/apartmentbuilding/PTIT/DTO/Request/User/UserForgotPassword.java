package com.apartmentbuilding.PTIT.DTO.Request.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UserForgotPassword {
    @NotNull(message = "CODE_NOT_NULL_OR_EMPTY")
    @NotBlank(message = "CODE_NOT_NULL_OR_EMPTY")
    private String code;
    @Size(min = 8, message = "PASSWORD_LENGTH_NOT_CORRECT")
    private String newPassword;
    @Size(min = 8, message = "PASSWORD_LENGTH_NOT_CORRECT")
    private String confirmPassword;
}
