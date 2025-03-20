package com.apartmentbuilding.PTIT.Controller;

import com.apartmentbuilding.PTIT.DTO.Reponse.APIResponse;
import com.apartmentbuilding.PTIT.DTO.Reponse.JwtResponse;
import com.apartmentbuilding.PTIT.DTO.Reponse.UserResponse;
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
@RequestMapping(value = "/${api_prefix}/users")
public class UserController {
    private final IUserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<APIResponse> login(@Valid @RequestBody UserLoginRequest request, HttpServletRequest httpServletRequest) {
        String device = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);
        JwtResponse jwtResponse = userService.login(request, device);
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.OK.value())
                .data(jwtResponse)
                .message("SUCCESS")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/login/google")
    public ResponseEntity<APIResponse> loginGoogle(@Valid @RequestBody UserSocialLogin request, HttpServletRequest httpServletRequest) {
        String device = httpServletRequest.getHeader(HttpHeaders.USER_AGENT);
        JwtResponse jwtResponse = userService.loginSocial(request, device);
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.OK.value())
                .data(jwtResponse)
                .message("SUCCESS")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<APIResponse> logout(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        userService.logout(token);
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.OK.value())
                .message("SUCCESS")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "/my-info")
    public ResponseEntity<APIResponse> myInfo() {
        UserResponse userResponse = userService.getMyInfo();
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.OK.value())
                .data(userResponse)
                .message("SUCCESS")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<APIResponse> register(@Valid @RequestBody UserRegister userRegister) {
        UserResponse userResponse = userService.register(userRegister);
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.CREATED.value())
                .data(userResponse)
                .message("SUCCESS")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<APIResponse> changePassword(@Valid @RequestBody UserChangePasswordRequest userChangePasswordRequest) {
        userService.changePassword(userChangePasswordRequest);
        APIResponse response = APIResponse.builder()
                .code(HttpStatus.OK.value())
                .message("SUCCESS")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<APIResponse> sendEmailForgotPassword(@RequestBody UserForgotPasswordSendEmail userForgotPasswordSendEmail) {
        userService.sendEmailForgotPassword(userForgotPasswordSendEmail.getEmail());
        APIResponse response = APIResponse.builder()
                .code(200)
                .message("SUCCESS")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(value = "/forgot-password")
    public ResponseEntity<APIResponse> verifyForgotPassword(@Valid @RequestBody UserForgotPassword userForgotPassword) {
        userService.verifyCodeForgotPassword(userForgotPassword);
        APIResponse response = APIResponse.builder()
                .code(200)
                .message("SUCCESS")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/token-valid")
    public ResponseEntity<APIResponse> verifyToken(@Valid @RequestBody TokenRequest tokenRequest, HttpServletRequest httpServletRequest) {
        JwtResponse jwtResponse = userService.validateToken(tokenRequest, httpServletRequest.getHeader(HttpHeaders.USER_AGENT));
        APIResponse response = APIResponse.builder()
                .code(200)
                .message("SUCCESS")
                .data(jwtResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
