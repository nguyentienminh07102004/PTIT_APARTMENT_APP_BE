package com.apartmentbuilding.PTIT.Configuration.Security;

import com.apartmentbuilding.PTIT.Common.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
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
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
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
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
//@EnableWebSocketSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    @Value(value = "${secret_key}")
    private String signingKey;
    private final JwtUtils jwtUtils;
    private final IJwtRepository jwtRepository;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(request -> request
                .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/login/google").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/refresh-token").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/logout").access(new WebExpressionAuthorizationManager("not isAnonymous()"))
                .requestMatchers(HttpMethod.POST, "/users/forgot-password").permitAll()
                .requestMatchers(HttpMethod.PUT, "/users/forgot-password").permitAll()
                .requestMatchers(HttpMethod.POST, "/users/token-valid").access(new WebExpressionAuthorizationManager("not isAnonymous()"))
                .requestMatchers(HttpMethod.GET, "/users/my-info").access(new WebExpressionAuthorizationManager("not isAnonymous()"))

                .requestMatchers(HttpMethod.POST, "/electrics").access(isNotUserAccess())
                .requestMatchers(HttpMethod.POST, "/electrics/read-excel").access(isNotUserAccess())
                .requestMatchers(HttpMethod.GET, "/electrics/search-all").hasRole(ConstantConfig.USER_ROLE)
                .requestMatchers(HttpMethod.GET, "/electrics").access(isNotUserAccess())

                .requestMatchers(HttpMethod.GET, "/waters").access(isNotUserAccess())
                .requestMatchers(HttpMethod.POST, "/waters/read-excel").access(isNotUserAccess())
                .requestMatchers(HttpMethod.PUT, "/waters").access(isNotUserAccess())

                .requestMatchers(HttpMethod.POST, "/apartments").access(isNotUserAccess())
                .requestMatchers(HttpMethod.GET, "/apartments").access(isNotUserAccess())
                .requestMatchers(HttpMethod.GET, "/apartments/my-apartments").hasRole(ConstantConfig.USER_ROLE)

                .requestMatchers(HttpMethod.GET, "/amenities").permitAll()

                .requestMatchers(HttpMethod.GET, "/buildings").permitAll()
                .requestMatchers(HttpMethod.PUT, "/buildings").access(isNotUserAccess())

                .requestMatchers(HttpMethod.POST, "/service-invoices").access(isNotUserAccess())
                .requestMatchers(HttpMethod.GET, "/service-invoices/all").access(isNotUserAccess())
                .requestMatchers(HttpMethod.GET, "/service-invoices/my-invoices").hasRole(ConstantConfig.USER_ROLE)

                .requestMatchers(HttpMethod.POST, "/requests/send-request").hasRole(ConstantConfig.USER_ROLE)
                .requestMatchers(HttpMethod.GET, "/requests").access(isNotUserAccess())

                .requestMatchers(HttpMethod.POST, "/notifications").hasRole(ConstantConfig.ADMIN_ROLE)
                .requestMatchers(HttpMethod.GET, "/notifications").hasRole(ConstantConfig.ADMIN_ROLE)
                .requestMatchers(HttpMethod.GET, "/notifications/top-5-notifications").hasRole(ConstantConfig.USER_ROLE)
                .requestMatchers(HttpMethod.PUT, "/notifications/change-is-read/{notificationId}").hasRole(ConstantConfig.USER_ROLE)

                .requestMatchers("/data-statistics/**").access(isNotUserAccess())

                .requestMatchers("/ws**").permitAll()
                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated());
        http.oauth2ResourceServer(oauth2 ->
                oauth2
                        .jwt(jwt -> jwt.decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint((httpServletRequest, httpServletResponse, authException) -> {
                            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authException.getMessage()));
                        }));
        http.cors(corsConfigurer -> corsFilter());
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
    public AuthorizationManager<RequestAuthorizationContext> isNotUserAccess() {
        return (authentication, object) -> {
            boolean isUser = authentication.get().getAuthorities()
                    .stream()
                    .anyMatch(role -> role.getAuthority().endsWith(ConstantConfig.USER_ROLE));
            return new AuthorizationDecision(!isUser);
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
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:5500", "http://localhost:5500"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
