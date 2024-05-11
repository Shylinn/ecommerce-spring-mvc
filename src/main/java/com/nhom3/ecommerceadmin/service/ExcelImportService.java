package com.nhom3.ecommerceadmin.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nhom3.ecommerceadmin.models.Customer;
import com.nhom3.ecommerceadmin.repository.CustomerRepository;

@Service
public class ExcelImportService {
    @Autowired
    private CustomerRepository customerRepository;

    public String importCustomersFromExcel(MultipartFile file) throws IOException {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Iterator<Cell> cellInRow = currentRow.iterator();
                Customer customer = new Customer();
                int cellIndex = 0;
                while (cellInRow.hasNext()) {
                    Cell currentCell = cellInRow.next();
                    switch (cellIndex) {
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
                            LocalDate birthDate = LocalDate.parse(currentCell.getStringCellValue());
                            customer.setDayOfBirth(birthDate);
                            break;
                        case 4:
                            customer.setAddress(currentCell.getStringCellValue());
                            break;

                    }
                    cellIndex++;

                }
                customerRepository.save(customer);

            }

            workbook.close();
            return "Import from Excel successful!";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to import data from Excel: " + e.getMessage();
        }

    }

}
