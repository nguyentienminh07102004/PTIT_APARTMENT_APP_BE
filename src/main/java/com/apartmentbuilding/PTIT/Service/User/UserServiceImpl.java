package com.apartmentbuilding.PTIT.Service.User;

import com.apartmentbuilding.PTIT.Beans.ConstantConfig;
import com.apartmentbuilding.PTIT.DTO.DTO.JwtDTO;
import com.apartmentbuilding.PTIT.DTO.Reponse.JwtResponse;
import com.apartmentbuilding.PTIT.DTO.Reponse.UserResponse;
import com.apartmentbuilding.PTIT.DTO.Request.User.TokenRequest;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserChangePasswordRequest;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserForgotPassword;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserLoginRequest;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserRegister;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserSocialLogin;
import com.apartmentbuilding.PTIT.Domains.JwtEntity;
import com.apartmentbuilding.PTIT.Domains.RoleEntity;
import com.apartmentbuilding.PTIT.Domains.UserEntity;
import com.apartmentbuilding.PTIT.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.ExceptionAdvice.ExceptionVariable;
import com.apartmentbuilding.PTIT.Mapper.User.UserMapper;
import com.apartmentbuilding.PTIT.Redis.RedisHash.CodeForgotPassword;
import com.apartmentbuilding.PTIT.Redis.Service.CodeForgotPassword.ICodeForgotPasswordService;
import com.apartmentbuilding.PTIT.Repository.IJwtRepository;
import com.apartmentbuilding.PTIT.Repository.IUserRepository;
import com.apartmentbuilding.PTIT.Service.Role.IRoleService;
import com.apartmentbuilding.PTIT.Utils.JwtUtils;
import com.apartmentbuilding.PTIT.Utils.SendEmailUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final IRoleService roleService;
    private final IJwtRepository jwtRepository;
    private final UserMapper userMapper;
    private final ICodeForgotPasswordService codeForgotPasswordService;
    private final SendEmailUtils sendEmailUtils;

    @Value(value = "${google_access_token_url}")
    private String accessTokenUrl;
    @Value(value = "${google_user_info_url}")
    private String userInfoUrl;
    @Value(value = "${google_client_id}")
    private String clientId;
    @Value(value = "${google_client_secret}")
    private String clientSecret;

    @Override
    @Transactional
    public JwtResponse login(@Valid UserLoginRequest request, String device) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.EMAIL_PASSWORD_NOT_MATCH));
        JwtEntity jwt = user.getJwt();
        if (jwt != null) {
            throw new DataInvalidException(ExceptionVariable.ACCOUNT_LOGIN_IN_OTHER_DEVICE);
        }
        if (request.getIsSocial() != null && !request.getIsSocial()) {
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new DataInvalidException(ExceptionVariable.EMAIL_PASSWORD_NOT_MATCH);
            }
        }
        return getJwtResponse(device, user);
    }

    @Override
    @Transactional
    public JwtResponse loginSocial(UserSocialLogin userSocialLogin, String device) {
        MultiValueMap<String, String> properties = new LinkedMultiValueMap<>();
        properties.add(OAuth2ParameterNames.CLIENT_ID, clientId);
        properties.add(OAuth2ParameterNames.CLIENT_SECRET, clientSecret);
        properties.add(OAuth2ParameterNames.GRANT_TYPE, AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        properties.add(OAuth2ParameterNames.REDIRECT_URI, userSocialLogin.getRedirectUri());
        properties.add(OAuth2ParameterNames.CODE, userSocialLogin.getCode());
        WebClient webClient = WebClient.create();
        String accessToken = Objects.requireNonNull(webClient.method(HttpMethod.POST)
                        .uri(accessTokenUrl)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .body(BodyInserters.fromFormData(properties))
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block())
                .get(OAuth2ParameterNames.ACCESS_TOKEN)
                .toString();
        String email = Objects.requireNonNull(webClient.method(HttpMethod.GET)
                        .uri(userInfoUrl)
                        .header(HttpHeaders.AUTHORIZATION, ConstantConfig.AUTHORIZATION_PREFIX + accessToken)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block())
                .get("email")
                .toString();
        if (userRepository.existsByEmail(email)) {
            return this.login(new UserLoginRequest(email, null, true), device);
        }
        UserEntity user = UserEntity.builder()
                .email(email)
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .role(roleService.findByCode(ConstantConfig.USER_ROLE))
                .build();
        return getJwtResponse(device, user);
    }

    @Transactional
    public JwtResponse getJwtResponse(String device, UserEntity user) {
        JwtDTO jwtDTO = jwtUtils.generateToken(user);
        JwtEntity jwt = jwtUtils.toEntity(jwtDTO, device, user);
        user.setJwt(jwt);
        userRepository.save(user);
        return new JwtResponse(jwt.getId(), jwtDTO.getToken(), jwt.getRefreshToken(), jwtDTO.getExpires(), jwt.getExpiry());
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.USER_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getMyInfo() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userMapper.userEntityToUserResponse(this.getUserByEmail(email));
    }

    @Override
    @Transactional
    public void changePassword(UserChangePasswordRequest userChangePasswordRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = this.getUserByEmail(email);
        if (passwordEncoder.matches(userChangePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new DataInvalidException(ExceptionVariable.OLD_PASSWORD_NEW_PASSWORD_MATCH);
        }
        if (!userChangePasswordRequest.getConfirmPassword().equals(userChangePasswordRequest.getNewPassword())) {
            throw new DataInvalidException(ExceptionVariable.PASSWORD_CONFIRM_PASSWORD_NOT_MATCH);
        }
        user.setPassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void logout(String token) {
        if (!StringUtils.hasText(token)) {
            throw new DataInvalidException(ExceptionVariable.UNAUTHORIZED);
        }
        token = token.substring(7);
        JWTClaimsSet claimsSet = jwtUtils.verify(token);
        String tokenId = claimsSet.getJWTID();
        JwtEntity jwt = jwtRepository.findById(tokenId)
                .orElseThrow(() -> new DataInvalidException(ExceptionVariable.TOKEN_INVALID));
        UserEntity user = jwt.getUser();
        jwt.setUser(null);
        user.setJwt(null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserResponse register(UserRegister userRegister) {
        if (!userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
            throw new DataInvalidException(ExceptionVariable.PASSWORD_CONFIRM_PASSWORD_NOT_MATCH);
        }
        String roleCode = ConstantConfig.USER_ROLE;
        if (StringUtils.hasText(userRegister.getRoleCode())) {
            roleCode = userRegister.getRoleCode();
        }
        RoleEntity role = roleService.findByCode(roleCode);
        UserEntity userEntity = userMapper.userRegisterToUserEntity(userRegister);
        userEntity.setRole(role);
        userEntity.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        userRepository.save(userEntity);
        return userMapper.userEntityToUserResponse(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existByIdentityNumber(String identityNumber) {
        return userRepository.existsByIdentityNumber(identityNumber);
    }

    @Override
    @Transactional
    public void sendEmailForgotPassword(String email) {
        String code = UUID.randomUUID().toString();
        Map<String, Object> properties = Map.of("code", code);
        if (!this.existEmail(email)) {
            throw new DataInvalidException(ExceptionVariable.USER_NOT_FOUND);
        }
        this.codeForgotPasswordService.setCodeForgotPassword(new CodeForgotPassword(code, email));
        this.sendEmailUtils.sendEmail(email, "Forgot Password", "forgotPassword", properties);
    }

    @Override
    @Transactional
    public void verifyCodeForgotPassword(UserForgotPassword userForgotPassword) {
        CodeForgotPassword codeForgotPassword = this.codeForgotPasswordService.getCodeForgotPassword(userForgotPassword.getCode());
        if (codeForgotPassword == null) {
            throw new DataInvalidException(ExceptionVariable.CODE_INVALID);
        }
        if (!userForgotPassword.getNewPassword().equals(userForgotPassword.getConfirmPassword())) {
            throw new DataInvalidException(ExceptionVariable.PASSWORD_CONFIRM_PASSWORD_NOT_MATCH);
        }
        String email = codeForgotPassword.getEmail();
        UserEntity user = this.getUserByEmail(email);
        if (passwordEncoder.matches(userForgotPassword.getNewPassword(), user.getPassword())) {
            throw new DataInvalidException(ExceptionVariable.OLD_PASSWORD_NEW_PASSWORD_MATCH);
        }
        user.setPassword(passwordEncoder.encode(userForgotPassword.getNewPassword()));
        user.setJwt(null);
        this.codeForgotPasswordService.deleteCodeForgotPassword(userForgotPassword.getCode());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public JwtResponse validateToken(TokenRequest tokenRequest, String device) {
        JWTClaimsSet jwtClaimsSet = jwtUtils.verify(tokenRequest.getToken());
        if (jwtClaimsSet.getExpirationTime().after(new Date(System.currentTimeMillis()))) {
            return new JwtResponse(jwtClaimsSet.getJWTID(), tokenRequest.getToken(), tokenRequest.getRefreshToken(), jwtClaimsSet.getExpirationTime(), null);
        }
        JwtEntity jwtEntity = jwtRepository.findById(jwtClaimsSet.getJWTID()).orElse(null);
        if (jwtEntity != null) {
            if (jwtEntity.getExpiry().before(new Date(System.currentTimeMillis()))) {
                throw new DataInvalidException(ExceptionVariable.UNAUTHORIZED);
            }
            UserEntity user = jwtEntity.getUser();
            user.getJwt().setUser(null);
            user.setJwt(null);
            jwtRepository.delete(user.getJwt());
            return this.getJwtResponse(device, user);
        }
        throw new DataInvalidException(ExceptionVariable.UNAUTHORIZED);
    }


    @Scheduled(cron = "@daily")
    @Transactional
    protected void deleteAllRefreshTokenExpired() {
        jwtRepository.deleteAllByExpiryBefore(new Date(System.currentTimeMillis()));
    }
}