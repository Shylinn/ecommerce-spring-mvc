package com.nhom3.ecommerceadmin.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.mapper.ProductMapper;
import com.nhom3.ecommerceadmin.models.Club;
import com.nhom3.ecommerceadmin.models.Product;
import com.nhom3.ecommerceadmin.repository.ProductRepository;
import com.nhom3.ecommerceadmin.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.nhom3.ecommerceadmin.mapper.ClubMapper.mapToClubDto;
import static com.nhom3.ecommerceadmin.mapper.ProductMapper.mapToProduct;
import static com.nhom3.ecommerceadmin.mapper.ProductMapper.mapToProductDto;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void saveProduct(ProductDto productDto) {
        Product product = mapToProduct(productDto);
        productRepository.save(product);
    }

    @Override
    public List<ProductDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::mapToProductDto).collect(Collectors.toList());
    }

    public void exportProductsToExcel(HttpServletResponse response) throws IOException {
        List<Product> products = productRepository.findAll();

        // Create Excel workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Code", "Photo URL", "Unit", "Quantity", "Price", "Author", "Publisher", "Genre", "Description", "Created On", "Updated On"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Fill data rows
        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getId());
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getCode());
            row.createCell(3).setCellValue(product.getPhotoUrl());
            row.createCell(4).setCellValue(product.getUnit());
            row.createCell(5).setCellValue(product.getQuantity());
            row.createCell(6).setCellValue(product.getPrice());
            row.createCell(7).setCellValue(product.getAuthor());
            row.createCell(8).setCellValue(product.getPublisher());
            row.createCell(9).setCellValue(product.getGenre());
            row.createCell(10).setCellValue(product.getDescription());
            row.createCell(11).setCellValue(product.getCreatedOn().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            row.createCell(12).setCellValue(product.getUpdatedOn().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        // Set content type and headers for the response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");

        // Write Excel workbook to the response output stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
