package com.apartmentbuilding.PTIT.Utils.Validate.EmailHasExists;

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
@Constraint(validatedBy = {EmailHasExistsValidate.class})
public @interface EmailHasExists {
    String message() default "EMAIL_HAS_EXISTS";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
