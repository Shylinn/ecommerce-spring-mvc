package com.nhom3.ecommerceadmin;

import org.apache.poi.ss.usermodel.Cell;

public class Utilities {
    public static String getStringCellValue(Cell cell) {
        if (cell != null) {
            return cell.getStringCellValue();
        } else {
            return "";
        }
    }

    public static Long getNumericCellValue(Cell cell) {
        if (cell != null) {
            return (long) cell.getNumericCellValue();
        } else {
            return 0L;
        }
    }
}
