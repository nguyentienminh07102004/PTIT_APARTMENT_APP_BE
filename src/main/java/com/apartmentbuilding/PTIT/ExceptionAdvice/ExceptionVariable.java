package com.apartmentbuilding.PTIT.ExceptionAdvice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionVariable {
    TOKEN_INVALID(400, "Token is invalid", HttpStatus.BAD_REQUEST),

    IDENTITY_NUMBER_HAS_EXISTS(400, "Identity number has exists", HttpStatus.BAD_REQUEST),
    IDENTITY_NUMBER_NOT_NULL_OR_EMPTY(400, "Identity number is not null or empty", HttpStatus.BAD_REQUEST),

    PASSWORD_CONFIRM_PASSWORD_NOT_MATCH(400, "Password confirmation password is not match", HttpStatus.BAD_REQUEST),
    PASSWORD_LENGTH_NOT_CORRECT(400, "Password length is not correct", HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_NEW_PASSWORD_MATCH(400, "Old password and new password is match", HttpStatus.BAD_REQUEST),

    EMAIL_PASSWORD_NOT_MATCH(400, "Email or password is not match", HttpStatus.BAD_REQUEST),
    EMAIL_HAS_EXISTS(400, "Email already exists", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(400, "Email is invalid", HttpStatus.BAD_REQUEST),

    ACCOUNT_LOGIN_IN_OTHER_DEVICE(400, "Account is login in an other device", HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND(400, "User Id is not exists", HttpStatus.BAD_REQUEST),

    ROLE_NOT_FOUND(404, "Role is not exists", HttpStatus.NOT_FOUND),

    FILE_EMPTY(400, "file is empty", HttpStatus.BAD_REQUEST),
    FILE_FORMAT_NOT_SUPPORTED(400, "File format is not supported", HttpStatus.BAD_REQUEST),
    FILE_EXCEL_NAME_INVALID(400, "File excel name is invalid", HttpStatus.BAD_REQUEST),
    FILE_HAS_IMPORTED(400, "File has imported", HttpStatus.BAD_REQUEST),
    FILE_EXCEL_NAME_NOT_EXIST(400, "File excel name is not exist", HttpStatus.BAD_REQUEST),

    SERVER_ERROR(500, "Server error", HttpStatus.INTERNAL_SERVER_ERROR),

    UNAUTHORIZED(401, "Unauthorized", HttpStatus.UNAUTHORIZED),

    APARTMENT_NOT_FOUND(404, "Apartment not found", HttpStatus.NOT_FOUND),
    APARTMENT_NAME_NOT_NULL_OR_EMPTY(400, "Apartment name is not null or empty", HttpStatus.BAD_REQUEST),

    MONTHLY_INVOICE_NOT_FOUND(404, "Monthly invoice not found", HttpStatus.NOT_FOUND),

    ELECTRIC_WATER_NOT_FOUND(404, "Electric water not found", HttpStatus.NOT_FOUND),

    CODE_NOT_NULL_OR_EMPTY(400, "Code is not null or empty", HttpStatus.BAD_REQUEST),
    CODE_INVALID(400, "Code is invalid", HttpStatus.BAD_REQUEST ),

    BUILDING_NOT_FOUND(404, "Building not found", HttpStatus.NOT_FOUND),;

    private final Integer code;
    private final String message;
    private final HttpStatus status;
}
