package com.apartmentbuilding.PTIT.Common.ExceptionAdvice;

import com.apartmentbuilding.PTIT.Common.Enums.ExceptionVariable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataInvalidException extends RuntimeException {
    private ExceptionVariable exceptionVariable;
}
