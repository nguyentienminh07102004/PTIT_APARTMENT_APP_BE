package com.apartmentbuilding.PTIT.Common.Validate.EmailHasExists;

import com.apartmentbuilding.PTIT.Service.User.IUserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailHasExistsValidate implements ConstraintValidator<EmailHasExists, String> {
    private final IUserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.existEmail(email);
    }
}
