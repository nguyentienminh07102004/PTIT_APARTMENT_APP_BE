package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Response.JwtResponse;
import com.apartmentbuilding.PTIT.DTO.Response.UserResponse;
import com.apartmentbuilding.PTIT.DTO.Request.User.TokenRequest;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserChangePasswordRequest;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserForgotPassword;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserForgotPasswordSendEmail;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserLoginRequest;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserRegister;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserSocialLogin;
import com.apartmentbuilding.PTIT.Service.User.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {
    private final IUserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody UserLoginRequest request, HttpServletRequest httpServletRequest) {
        String device = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);
        JwtResponse jwtResponse = userService.login(request, device);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping(value = "/login/google")
    public ResponseEntity<JwtResponse> loginGoogle(@Valid @RequestBody UserSocialLogin request, HttpServletRequest httpServletRequest) {
        String device = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);
        JwtResponse jwtResponse = userService.loginSocial(request, device);
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        userService.logout(token);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/my-info")
    public ResponseEntity<UserResponse> myInfo() {
        UserResponse userResponse = userService.getMyInfo();
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegister userRegister) {
        UserResponse userResponse = userService.register(userRegister);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        userService.changePassword(userChangePasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<Void> sendEmailForgotPassword(@Valid @RequestBody UserForgotPasswordSendEmail userForgotPasswordSendEmail) {
        userService.sendEmailForgotPassword(userForgotPasswordSendEmail.getEmail());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/forgot-password")
    public ResponseEntity<Void> verifyForgotPassword(@Valid @RequestBody UserForgotPassword userForgotPassword) {
        userService.verifyCodeForgotPassword(userForgotPassword);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/token-valid")
    public ResponseEntity<JwtResponse> verifyToken(@Valid @RequestBody TokenRequest tokenRequest, HttpServletRequest httpServletRequest) {
        JwtResponse jwtResponse = userService.validateToken(tokenRequest, httpServletRequest.getHeader(HttpHeaders.USER_AGENT));
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }
}
