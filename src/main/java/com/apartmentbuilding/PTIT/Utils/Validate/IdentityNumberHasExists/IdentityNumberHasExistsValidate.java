package com.apartmentbuilding.PTIT.Utils.Validate.IdentityNumberHasExists;

import com.apartmentbuilding.PTIT.Service.User.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class IdentityNumberHasExistsValidate implements ConstraintValidator<IdentityNumberHasExists, String> {
    private final IUserService userService;

    @Override
    public boolean isValid(String identityNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasText(identityNumber)) {
            return true;
        }
        return !userService.existByIdentityNumber(identityNumber);
    }
}
