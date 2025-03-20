package com.apartmentbuilding.PTIT.DTO.Reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String id;
    private String token;
    private String refreshToken;
    private Date expires;
    private Date refreshExpires;
}
