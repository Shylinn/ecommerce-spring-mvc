package com.nhom3.ecommerceadmin.service.impl;

import com.nhom3.ecommerceadmin.dto.BillDto;
import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.mapper.BillMapper;
import com.nhom3.ecommerceadmin.mapper.ProductMapper;
import com.nhom3.ecommerceadmin.models.Bill;
import com.nhom3.ecommerceadmin.models.Product;
import com.nhom3.ecommerceadmin.repository.BillRepository;
import com.nhom3.ecommerceadmin.repository.ProductRepository;
import com.nhom3.ecommerceadmin.service.BillService;
import com.nhom3.ecommerceadmin.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static com.nhom3.ecommerceadmin.Utilities.getNumericCellValue;
import static com.nhom3.ecommerceadmin.Utilities.getStringCellValue;
import static com.nhom3.ecommerceadmin.mapper.ProductMapper.mapToProduct;
import static com.nhom3.ecommerceadmin.mapper.ProductMapper.mapToProductDto;

@Service
public class BillServiceImpl implements BillService {

    private BillRepository billRepository;
    @Autowired
    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }
//
//    @Override
//    public void saveProduct(ProductDto productDto) {
//        Product product = mapToProduct(productDto);
//        productRepository.save(product);
//    }
//
    @Override
    public List<BillDto> findAllBills() {
        List<Bill> bills = billRepository.findAll();
        return bills.stream().map(BillMapper::mapToBillDto).collect(Collectors.toList());
    }
//
//    public void exportProductsToExcel(HttpServletResponse response) throws IOException {
//        List<Product> products = productRepository.findAll();
//
//        // Create Excel workbook and sheet
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Products");
//
//        // Create header row
//        Row headerRow = sheet.createRow(0);
//        String[] headers = {"ID", "Name", "Code", "Photo URL", "Unit", "Quantity", "Price", "Author", "Publisher", "Genre", "Description", "Created On", "Updated On"};
//        for (int i = 0; i < headers.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(headers[i]);
//        }
//
//        // Fill data rows
//        int rowNum = 1;
//        for (Product product : products) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(product.getId());
//            row.createCell(1).setCellValue(product.getName());
//            row.createCell(2).setCellValue(product.getCode());
//            row.createCell(3).setCellValue(product.getPhotoUrl());
//            row.createCell(4).setCellValue(product.getUnit());
//            row.createCell(5).setCellValue(product.getQuantity());
//            row.createCell(6).setCellValue(product.getPrice());
//            row.createCell(7).setCellValue(product.getAuthor());
//            row.createCell(8).setCellValue(product.getPublisher());
//            row.createCell(9).setCellValue(product.getGenre());
//            row.createCell(10).setCellValue(product.getDescription());
//            row.createCell(11).setCellValue(product.getCreatedOn().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//            row.createCell(12).setCellValue(product.getUpdatedOn().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//        }
//
//        // Set content type and headers for the response
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");
//
//        // Write Excel workbook to the response output stream
//        workbook.write(response.getOutputStream());
//        workbook.close();
//    }
//
//    public void addProductsFromExcel(MultipartFile file) throws IOException {
//        try (InputStream inputStream = file.getInputStream()) {
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(0); // Giả sử file Excel có một sheet đầu tiên
//
//            Iterator<Row> iterator = sheet.iterator();
//            while (iterator.hasNext()) {
//                Row currentRow = iterator.next();
//                // Bỏ qua dòng header
//                if (currentRow.getRowNum() == 0) {
//                    continue;
//                }
//
//                try {
//                    // Đọc thông tin sản phẩm từ các ô trong dòng
//                    String name = getStringCellValue(currentRow.getCell(1));
//                    String code = getStringCellValue(currentRow.getCell(2));
//                    String photoUrl = getStringCellValue(currentRow.getCell(3));
//                    String unit = getStringCellValue(currentRow.getCell(4));
//                    String quantity = getStringCellValue(currentRow.getCell(5));
//                    Long price = getNumericCellValue(currentRow.getCell(6));
//                    String author = getStringCellValue(currentRow.getCell(7));
//                    String publisher = getStringCellValue(currentRow.getCell(8));
//                    String genre = getStringCellValue(currentRow.getCell(9));
//                    String description = getStringCellValue(currentRow.getCell(10));
//
//                    // Tạo đối tượng Product và lưu vào cơ sở dữ liệu
//                    Product product = new Product();
//                    product.setName(name);
//                    product.setCode(code);
//                    product.setPhotoUrl(photoUrl);
//                    product.setUnit(unit);
//                    product.setQuantity(quantity);
//                    product.setPrice(price);
//                    product.setAuthor(author);
//                    product.setPublisher(publisher);
//                    product.setGenre(genre);
//                    product.setDescription(description);
//
//                    productRepository.save(product);
//                } catch (Exception e) {
//                    // Xử lý trường hợp có lỗi xảy ra trong quá trình đọc dữ liệu từ file Excel
//                    throw new RuntimeException("Lỗi xảy ra khi đọc dữ liệu từ file Excel. Dòng: " + (currentRow.getRowNum() + 1), e);
//                }
//            }
//            workbook.close();
//        } catch (IOException e) {
//            throw new IOException("Lỗi xảy ra khi đọc file Excel", e);
//        }
//    }
//
//    public void deleteProductById(Long id) {
//        productRepository.deleteById(id);
//    }
//
//    @Override
//    public ProductDto findProductById(Long productId) {
//        return mapToProductDto(productRepository.findById(productId).get());
//    }
}
