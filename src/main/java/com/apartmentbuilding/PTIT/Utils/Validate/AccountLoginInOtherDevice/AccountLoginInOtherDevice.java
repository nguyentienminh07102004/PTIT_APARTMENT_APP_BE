package com.apartmentbuilding.PTIT.Utils.Validate.AccountLoginInOtherDevice;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {AccountLoginInOtherDeviceValidate.class})
public @interface AccountLoginInOtherDevice {
    String message() default "ACCOUNT_LOGIN_IN_OTHER_DEVICE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
