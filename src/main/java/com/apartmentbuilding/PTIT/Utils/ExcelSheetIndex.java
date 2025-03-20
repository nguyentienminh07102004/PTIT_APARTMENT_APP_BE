package com.apartmentbuilding.PTIT.Utils;

import com.apartmentbuilding.PTIT.ExceptionAdvice.DataInvalidException;
import com.apartmentbuilding.PTIT.ExceptionAdvice.ExceptionVariable;

import static com.apartmentbuilding.PTIT.Beans.ConstantConfig.ELECTRIC_TYPE;
import static com.apartmentbuilding.PTIT.Beans.ConstantConfig.VEHICLE_TYPE;
import static com.apartmentbuilding.PTIT.Beans.ConstantConfig.WATER_TYPE;

public class ExcelSheetIndex {
    public static Integer getSheetIndex(String sheetType) {
        return switch (sheetType) {
            case WATER_TYPE -> 0;
            case ELECTRIC_TYPE -> 1;
            case VEHICLE_TYPE -> 2;
            default -> throw new DataInvalidException(ExceptionVariable.SERVER_ERROR);
        };
    }
}
