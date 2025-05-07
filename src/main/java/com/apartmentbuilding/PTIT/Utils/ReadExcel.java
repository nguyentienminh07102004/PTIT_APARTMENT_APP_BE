package com.apartmentbuilding.PTIT.Utils;

import com.apartmentbuilding.PTIT.Common.Enum.ExceptionVariable;
import com.apartmentbuilding.PTIT.Common.ExceptionAdvice.DataInvalidException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class ReadExcel {
    public <T> List<T> readExcel(MultipartFile excelFile, int sheetNumber, Class<T> tClass) {
        try {
            InputStream inputStream = excelFile.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            Iterator<Row> rows = sheet.iterator();
            Map<String, Integer> headerExcelFile = new HashMap<>();
            List<T> result = new ArrayList<>();
            Map<Integer, Object> headerObjectExcelFile = new HashMap<>();
            while (rows.hasNext()) {
                Row row = rows.next();
                if (row.getRowNum() == 0) {
                    Iterator<Cell> cells = row.cellIterator();
                    while (cells.hasNext()) {
                        Cell cell = cells.next();
                        // header file excel
                        headerExcelFile.put(this.convertHeaderExcelFile(cell.getStringCellValue()), cell.getColumnIndex());
                    }
                } else {
                    Iterator<Cell> cells = row.cellIterator();
                    while (cells.hasNext()) {
                        Cell cell = cells.next();
                        Object data = switch (cell.getCellType()) {
                            case BOOLEAN -> cell.getBooleanCellValue();
                            case NUMERIC -> {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    yield cell.getDateCellValue();
                                } else {
                                    yield cell.getNumericCellValue();
                                }
                            }
                            case STRING -> cell.getStringCellValue();
                            case FORMULA -> {
                                CellValue cellValue = workbook.getCreationHelper().createFormulaEvaluator().evaluate(cell);
                                yield switch (cellValue.getCellType()) {
                                    case BOOLEAN -> cellValue.getBooleanValue();
                                    case NUMERIC -> cellValue.getNumberValue();
                                    case STRING -> cellValue.getStringValue();
                                    default -> null;
                                };
                            }
                            default -> null;
                        };
                        headerObjectExcelFile.put(cell.getColumnIndex(), data);
                    }
                    // set value
                    T t = tClass.getDeclaredConstructor().newInstance();
                    Field[] fields = tClass.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String fieldName = field.getName().strip().toLowerCase();
                        Integer columnIndex = headerExcelFile.getOrDefault(fieldName, null);
                        if (columnIndex == null) {
                            throw new DataInvalidException(ExceptionVariable.FILE_EXCEL_NAME_INVALID);
                        }
                        Object data = headerObjectExcelFile.get(columnIndex);
                        if (field.getType().isEnum()) {
                            field.set(t, Enum.valueOf((Class<Enum>) field.getType(), String.valueOf(data)));
                        } else if (field.getType().equals(Integer.class)) {
                            field.set(t, Double.valueOf(data.toString()).intValue());
                        } else if (field.getType().equals(Long.class)) {
                            field.set(t, Double.valueOf(data.toString()).longValue());
                        } else {
                            field.set(t, field.getType().cast(data));
                        }
                    }
                    result.add(t);
                }
            }
            return result;
        } catch (IOException | InvocationTargetException | IllegalAccessException | InstantiationException |
                 NoSuchMethodException e) {
            throw new DataInvalidException(ExceptionVariable.FILE_EXCEL_NAME_INVALID);
        }
    }

    private String convertHeaderExcelFile(String headerExcelFile) {
        return String.join("", headerExcelFile.strip().split("\\s+")).toLowerCase();
    }
}
