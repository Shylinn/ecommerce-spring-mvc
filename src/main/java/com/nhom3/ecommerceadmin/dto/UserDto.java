package com.nhom3.ecommerceadmin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotEmpty(message = "Nhập tên nhan vien")
    private String name;
    @NotEmpty(message = "Nhập mã nhan vien")
    private LocalDateTime datework;
    private String phone;
//
//    public String getFullPhotoPath(){
//        if(photoUrl==null || photoUrl.isEmpty())  {
//            return "https://picsum.photos/500/500";
//        }
//        //            String uploadFolderPath = new ClassPathResource("static/uploadImg").getFile().getAbsolutePath();
//        Path path = Paths.get("/uploadImg", photoUrl);
//        return path.toFile().getAbsolutePath();
//    }

    @NotEmpty(message = "nhap CCCD")
    private String identification;
    @NotNull(message = "Nhập dia chi")
    private String address;
    @NotNull(message = "Nhập doanh so")
    private Long sales;
}