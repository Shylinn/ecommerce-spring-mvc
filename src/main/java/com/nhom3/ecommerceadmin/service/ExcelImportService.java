package com.nhom3.ecommerceadmin.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhom3.ecommerceadmin.models.Customer;

@Service
public class ExcelImportService {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Full Name", "Email", "Phone Number", "Date Of Birth", "Address" };

    public static boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;

    }

    public List<Customer> excelToCustomerList(MultipartFile file) throws IOException {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<Customer> customerList = new ArrayList<Customer>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Customer customer = new Customer();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            customer.setFullName(currentCell.getStringCellValue());
                            break;
                        case 1:
                            customer.setEmail(currentCell.getStringCellValue());
                            break;
                        case 2:
                            customer.setPhoneNum(currentCell.getStringCellValue());
                            break;
                        case 3:
                            LocalDate dayofBirth = currentCell.getDateCellValue().toInstant()
                                    .atZone(ZoneId.systemDefault()).toLocalDate();
                            customer.setDayOfBirth(dayofBirth);
                            break;
                        case 4:
                            customer.setAddress(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;

                }
                customerList.add(customer);

            }
            workbook.close();
            return customerList;

        } catch (Exception e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

}
