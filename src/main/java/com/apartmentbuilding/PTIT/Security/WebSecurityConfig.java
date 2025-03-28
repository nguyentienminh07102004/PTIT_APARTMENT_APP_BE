package com.apartmentbuilding.PTIT.Security;

import com.apartmentbuilding.PTIT.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.DTO.Reponse.APIResponse;
import com.apartmentbuilding.PTIT.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.ExceptionAdvice.ExceptionVariable;
import com.apartmentbuilding.PTIT.Repository.IJwtRepository;
import com.apartmentbuilding.PTIT.Utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value(value = "${secret_key}")
    private String signingKey;
    private final JwtUtils jwtUtils;
    @Value(value = "${api_prefix}")
    private String apiPrefix;
    private final IJwtRepository jwtRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.POST, "/%s/users/register".formatted(apiPrefix)).permitAll()
                .requestMatchers(HttpMethod.POST, "/%s/users/login/google".formatted(apiPrefix)).permitAll()
                .requestMatchers(HttpMethod.POST, "/%s/users/login".formatted(apiPrefix)).permitAll()
                .requestMatchers(HttpMethod.POST, "/%s/users/refresh-token".formatted(apiPrefix)).permitAll()
                .requestMatchers(HttpMethod.POST, "/%s/users/logout".formatted(apiPrefix))
                    .access(new WebExpressionAuthorizationManager("not isAnonymous()"))
                .requestMatchers(HttpMethod.POST, "/%s/users/forgot-password".formatted(apiPrefix)).permitAll()
                .requestMatchers(HttpMethod.PUT, "/%s/users/forgot-password".formatted(apiPrefix)).permitAll()
                .requestMatchers(HttpMethod.POST, "/%s/users/token-valid".formatted(apiPrefix)).permitAll()

                .requestMatchers(HttpMethod.POST, "/%s/electric/".formatted(apiPrefix)).hasRole(ConstantConfig.ADMIN_ROLE)
                .requestMatchers(HttpMethod.GET, "/%s/electrics/apartment/{apartmentId}".formatted(apiPrefix))
                    .access(new WebExpressionAuthorizationManager("not isAnonymous()"))

                .requestMatchers(HttpMethod.POST, "/%s/apartments/".formatted(apiPrefix)).hasRole(ConstantConfig.ADMIN_ROLE)

                .requestMatchers("/%s/reports/**".formatted(apiPrefix)).permitAll()

                .requestMatchers("/%s/ai**".formatted(apiPrefix)).permitAll()

                .requestMatchers("/ws**").permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated());
        http.cors(cors -> corsFilter());
        http.oauth2ResourceServer(oauth2 ->
                oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint((httpServletRequest, httpServletResponse, authException) -> {
                            APIResponse response = APIResponse.builder()
                                    .code(HttpStatus.UNAUTHORIZED.value())
                                    .message(authException.getMessage())
                                    .build();
                            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(response));
                        }));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return token -> {
            // check jwt exists not in database
            JWTClaimsSet jwtClaimsSet = jwtUtils.verify(token);
            if (!jwtRepository.existsById(jwtClaimsSet.getJWTID())) {
                throw new DataInvalidException(ExceptionVariable.TOKEN_INVALID);
            }
            SecretKeySpec spec = new SecretKeySpec(signingKey.getBytes(), MacAlgorithm.HS512.getName());
            return NimbusJwtDecoder.withSecretKey(spec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build()
                    .decode(token);
        };
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return converter;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:3002", "http://localhost:3000", "http://127.0.0.1:5500", "http://localhost:5500"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
