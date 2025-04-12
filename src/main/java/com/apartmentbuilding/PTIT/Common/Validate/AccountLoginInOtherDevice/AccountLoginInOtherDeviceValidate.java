package com.apartmentbuilding.PTIT.Common.Validate.AccountLoginInOtherDevice;

import com.apartmentbuilding.PTIT.Model.Entity.JwtEntity;
import com.apartmentbuilding.PTIT.Model.Entity.UserEntity;
import com.apartmentbuilding.PTIT.Service.User.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountLoginInOtherDeviceValidate implements ConstraintValidator<AccountLoginInOtherDevice, String> {
    private final IUserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null || email.isEmpty()) {
            return true;
        }
        UserEntity user = userService.getUserByEmail(email);
        if (user == null) {
            return true;
        }
        JwtEntity jwt = user.getJwt();
        return jwt == null;
    }
}
