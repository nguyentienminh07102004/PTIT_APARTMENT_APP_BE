package com.apartmentbuilding.PTIT.Utils;

import com.apartmentbuilding.PTIT.DTO.DTO.JwtDTO;
import com.apartmentbuilding.PTIT.Model.Entity.JwtEntity;
import com.apartmentbuilding.PTIT.Model.Entity.UserEntity;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtils {
    @Value(value = "${secret_key}")
    private String secretKey;
    @Value(value = "${access_token_duration}")
    private Long accessTokenDuration;
    @Value(value = "${refresh_token_duration}")
    private Long refreshTokenDuration;
    public JwtDTO generateToken(UserEntity user) {
        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            String id = UUID.randomUUID().toString();
            Date exp = new Date(System.currentTimeMillis() + accessTokenDuration * 1000);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .jwtID(id)
                    .expirationTime(exp)
                    .subject(user.getEmail())
                    .issuer("BUILDING_MANAGER_PTIT")
                    .claim("scope", user.getRole().getCode())
                    .build();
            Payload payload = new Payload(claimsSet.toJSONObject());
            JWSObject jwsObject = new JWSObject(header, payload);
            JWSSigner signer = new MACSigner(secretKey.getBytes());
            jwsObject.sign(signer);
            return new JwtDTO(id, jwsObject.serialize(), exp);
        } catch (Exception exception) {
            throw new DataInvalidException(ExceptionVariable.SERVER_ERROR);
        }
    }

    public JWTClaimsSet verify(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secretKey);
            signedJWT.verify(verifier);
            return signedJWT.getJWTClaimsSet();
        } catch (Exception exception) {
            throw new DataInvalidException(ExceptionVariable.TOKEN_INVALID);
        }
    }

    public JwtEntity toEntity(JwtDTO jwtDTO, String device, UserEntity user) {
        return JwtEntity.builder()
                .id(jwtDTO.getId())
                .device(device)
                .user(user)
                .refreshToken(UUID.randomUUID().toString())
                .expiry(new Date(System.currentTimeMillis() + refreshTokenDuration * 1000))
                .build();
    }
}
