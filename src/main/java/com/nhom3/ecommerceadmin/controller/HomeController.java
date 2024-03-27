package com.nhom3.ecommerceadmin.controller;

import com.nhom3.ecommerceadmin.models.Image;
import com.nhom3.ecommerceadmin.models.Staff;
import com.nhom3.ecommerceadmin.repository.ImageRepository;
import com.nhom3.ecommerceadmin.security.SecurityUtil;
import com.nhom3.ecommerceadmin.service.ImageService;
import com.nhom3.ecommerceadmin.service.StaffService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class HomeController {
    private StaffService staffService;
    private ImageRepository imageRepository;
    private ResourceLoader resourceLoader;

    @Autowired
    public HomeController(StaffService staffService, ImageRepository imageRepository,ResourceLoader resourceLoader) {
        this.staffService = staffService;
        this.imageRepository = imageRepository;
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/index")
    public String indexPage(Model model){
//        model.addAttribute("dashboardActive",true);
        return "index";
    }
    @GetMapping("/")
    public String rootPage(Model model){
        return "redirect:/index";
    }

    @PostMapping("/imgUpload")
    public String imageUpload(@RequestParam MultipartFile photoUrl, HttpSession session){
        Image image = new Image();
        image.setImageName(photoUrl.getOriginalFilename());
        try{
            imageRepository.save(image);
            File saveFile = new ClassPathResource("static/uploadImg").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+ photoUrl.getOriginalFilename());

            String staticFolderPath = resourceLoader.getResource("classpath:static").getFile().getAbsolutePath();
            Path devPath = Paths.get(staticFolderPath+File.separator+ photoUrl.getOriginalFilename());

            Files.copy(photoUrl.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(photoUrl.getInputStream(), devPath, StandardCopyOption.REPLACE_EXISTING);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        session.setAttribute("msg", "Đã upload hình ảnh");

        return "redirect:/";
    }
}
