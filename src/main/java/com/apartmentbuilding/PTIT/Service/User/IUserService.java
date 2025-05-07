package com.apartmentbuilding.PTIT.Service.User;

import com.apartmentbuilding.PTIT.DTO.Request.User.UserChangePasswordRequest;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserForgotPassword;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserLoginRequest;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserRegister;
import com.apartmentbuilding.PTIT.DTO.Request.User.UserSocialLogin;
import com.apartmentbuilding.PTIT.DTO.Response.JwtResponse;
import com.apartmentbuilding.PTIT.DTO.Response.UserResponse;
import com.apartmentbuilding.PTIT.Model.Entity.UserEntity;
import org.springframework.security.core.Authentication;

public interface IUserService {
    JwtResponse login(UserLoginRequest request, String device);
    JwtResponse loginSocial(UserSocialLogin request, String device);
    UserEntity getUserByEmail(String email);
    UserResponse getMyInfo(Authentication authentication);
    void changePassword(UserChangePasswordRequest userChangePasswordRequest);
    void logout(String token);
    UserResponse register(UserRegister userRegister);
    boolean existEmail(String email);
    boolean existByIdentityNumber(String identityNumber);
    void sendEmailForgotPassword(String email);
    void verifyCodeForgotPassword(UserForgotPassword userForgotPassword);
}
