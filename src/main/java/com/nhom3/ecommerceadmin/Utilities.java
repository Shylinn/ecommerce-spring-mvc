package com.nhom3.ecommerceadmin;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class Utilities {
    public static String getStringCellValue(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            } else if (cell.getCellType() == CellType.BOOLEAN) {
                return String.valueOf(cell.getBooleanCellValue());
            } else if (cell.getCellType() == CellType.FORMULA) {
                switch (cell.getCachedFormulaResultType()) {
                    case NUMERIC:
                        return String.valueOf(cell.getNumericCellValue());
                    case STRING:
                        return cell.getStringCellValue();
                    case BOOLEAN:
                        return String.valueOf(cell.getBooleanCellValue());
                    default:
                        return "";
                }
            }
        }
        return "";
    }

    public static Long getNumericCellValue(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (long) cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                try {
                    return Long.parseLong(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    // Log the error or handle it as needed
                    System.err.println("Error parsing cell value as Long: " + cell.getStringCellValue());
                    return 0L;
                }
            } else if (cell.getCellType() == CellType.FORMULA) {
                switch (cell.getCachedFormulaResultType()) {
                    case NUMERIC:
                        return (long) cell.getNumericCellValue();
                    case STRING:
                        try {
                            return Long.parseLong(cell.getStringCellValue());
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing formula result as Long: " + cell.getStringCellValue());
                            return 0L;
                        }
                    default:
                        return 0L;
                }
            }
        }
        return 0L;
    }
}
