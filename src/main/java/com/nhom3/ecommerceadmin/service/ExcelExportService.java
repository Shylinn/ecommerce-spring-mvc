package com.nhom3.ecommerceadmin.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.nhom3.ecommerceadmin.models.Customer;
import java.io.IOException;

@Service
public class ExcelExportService {
    public void exportToExcel(jakarta.servlet.http.HttpServletResponse response, Iterable<Customer> customers)
            throws IOException {
        // Tạo một workbook mới
        Workbook workbook = new XSSFWorkbook();

        // Tạo một sheet mới
        Sheet sheet = workbook.createSheet("Customers");

        // Tạo header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("STT");
        headerRow.createCell(1).setCellValue("ID Customer");
        headerRow.createCell(2).setCellValue("Full Name");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Phone Number");
        headerRow.createCell(5).setCellValue("Date Of Birth");
        headerRow.createCell(6).setCellValue("Address");
        headerRow.createCell(7).setCellValue("Amount spent");

        // Đổ dữ liệu vào các row tiếp theo
        int rowNum = 1;
        for (Customer customer : customers) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum - 1);
            row.createCell(1).setCellValue(customer.getId());
            row.createCell(2).setCellValue(customer.getFullName());
            row.createCell(3).setCellValue(customer.getEmail());
            row.createCell(4).setCellValue(customer.getPhoneNum());
            row.createCell(5).setCellValue(customer.getdayOfBirth().toString());
            row.createCell(6).setCellValue(customer.getAddress());
            row.createCell(7).setCellValue(customer.getSales());
        }

        // Thiết lập HTTP response header
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");

        // Ghi workbook vào ServletOutputStream của response
        response.getOutputStream().flush();
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
