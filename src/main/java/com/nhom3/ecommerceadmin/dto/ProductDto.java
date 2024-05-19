package com.nhom3.ecommerceadmin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Builder
public class ProductDto {
    private Long id;
    @NotEmpty(message = "Nhập tên sản phẩm")
    private String name;
    @NotEmpty(message = "Nhập mã sản phẩm")
    private String code;
    private String photoUrl;
//
//    public String getFullPhotoPath(){
//        if(photoUrl==null || photoUrl.isEmpty())  {
//            return "https://picsum.photos/500/500";
//        }
//        //            String uploadFolderPath = new ClassPathResource("static/uploadImg").getFile().getAbsolutePath();
//        Path path = Paths.get("/uploadImg", photoUrl);
//        return path.toFile().getAbsolutePath();
//    }

    @NotEmpty(message = "Chọn đơn vị tính")
    private String unit;
    @NotNull(message = "Nhập số lượng")
    private String quantity;
    @NotNull(message = "Nhập giá sản phẩm")
    private Long price;
    private String author;
    private String publisher;
    private String genre;
    private String description;
}