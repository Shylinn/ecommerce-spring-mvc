package com.nhom3.ecommerceadmin.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.mapper.ProductMapper;
import com.nhom3.ecommerceadmin.models.Product;
import com.nhom3.ecommerceadmin.repository.ProductRepository;
import com.nhom3.ecommerceadmin.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.nhom3.ecommerceadmin.Utilities.getStringCellValue;
import static com.nhom3.ecommerceadmin.Utilities.getNumericCellValue;
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

        // Tạo Excel workbook và sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");

        // Tạo dòng header
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Code", "Photo URL", "Unit", "Quantity", "Price", "Author", "Publisher", "Genre", "Description", "Created On", "Updated On"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Tạo các dòng dữ liệu
        int rowNum = 1;
        for (Product product : products) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(Objects.toString(product.getId(), ""));
            row.createCell(1).setCellValue(Objects.toString(product.getName(), ""));
            row.createCell(2).setCellValue(Objects.toString(product.getCode(), ""));
            row.createCell(3).setCellValue(Objects.toString(product.getPhotoUrl(), ""));
            row.createCell(4).setCellValue(Objects.toString(product.getUnit(), ""));
            row.createCell(5).setCellValue(Objects.toString(product.getQuantity(), ""));
            row.createCell(6).setCellValue(Objects.toString(product.getPrice(), ""));
            row.createCell(7).setCellValue(Objects.toString(product.getAuthor(), ""));
            row.createCell(8).setCellValue(Objects.toString(product.getPublisher(), ""));
            row.createCell(9).setCellValue(Objects.toString(product.getGenre(), ""));
            row.createCell(10).setCellValue(Objects.toString(product.getDescription(), ""));

            if (product.getCreatedOn() != null) {
                row.createCell(11).setCellValue(product.getCreatedOn().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            } else {
                row.createCell(11).setCellValue("");
            }

            if (product.getUpdatedOn() != null) {
                row.createCell(12).setCellValue(product.getUpdatedOn().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            } else {
                row.createCell(12).setCellValue("");
            }
        }

        // Thiết lập content type và headers cho response
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=products.xlsx");

        // Ghi Excel workbook sang output stream của response
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    public void addProductsFromExcel(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Giả sử file Excel có một sheet đầu tiên

            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                // Bỏ qua dòng header
                if (currentRow.getRowNum() == 0) {
                    continue;
                }

                try {
                    // Đọc thông tin sản phẩm từ các ô trong dòng
                    String name = getStringCellValue(currentRow.getCell(1));
                    String code = getStringCellValue(currentRow.getCell(2));
                    String photoUrl = getStringCellValue(currentRow.getCell(3));
                    String unit = getStringCellValue(currentRow.getCell(4));
                    String quantity = getStringCellValue(currentRow.getCell(5));
                    Long price = getNumericCellValue(currentRow.getCell(6));
                    String author = getStringCellValue(currentRow.getCell(7));
                    String publisher = getStringCellValue(currentRow.getCell(8));
                    String genre = getStringCellValue(currentRow.getCell(9));
                    String description = getStringCellValue(currentRow.getCell(10));

                    // Tạo đối tượng Product và lưu vào cơ sở dữ liệu
                    Product product = new Product();
                    product.setName(name);
                    product.setCode(code);
                    product.setPhotoUrl(photoUrl);
                    product.setUnit(unit);
                    product.setQuantity(quantity);
                    product.setPrice(price);
                    product.setAuthor(author);
                    product.setPublisher(publisher);
                    product.setGenre(genre);
                    product.setDescription(description);

                    productRepository.save(product);
                } catch (Exception e) {
                    // Xử lý trường hợp có lỗi xảy ra trong quá trình đọc dữ liệu từ file Excel
                    throw new RuntimeException("Lỗi xảy ra khi đọc dữ liệu từ file Excel. Dòng: " + (currentRow.getRowNum() + 1), e);
                }
            }
            workbook.close();
        } catch (IOException e) {
            throw new IOException("Lỗi xảy ra khi đọc file Excel", e);
        }
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto findProductById(Long productId) {
        return mapToProductDto(productRepository.findById(productId).get());
    }
}
