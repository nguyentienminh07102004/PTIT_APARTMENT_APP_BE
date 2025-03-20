package com.apartmentbuilding.PTIT.DTO.Request.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UserSocialLogin {
    @NotNull(message = "CODE_NOT_NULL_OR_EMPTY")
    @NotBlank(message = "CODE_NOT_NULL_OR_EMPTY")
    private String code;
    private String redirectUri;
}
