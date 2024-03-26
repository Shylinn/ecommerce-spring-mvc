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

    @Autowired
    public HomeController(StaffService staffService, ImageRepository imageRepository) {
        this.staffService = staffService;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/index")
    public String indexPage(Model model){
        Staff staff = new Staff();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            staff = staffService.findByUsername(username);
            model.addAttribute("staff", staff);
        }
        model.addAttribute("staff", staff);
        return "index";
    }

    @PostMapping("/imgUpload")
    public String imageUpload(@RequestParam MultipartFile photoUrl, HttpSession session){
        Image image = new Image();
        image.setImageName(photoUrl.getOriginalFilename());
        try{
            imageRepository.save(image);
            File saveFile = new ClassPathResource("static/uploadImg").getFile();
            Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+ photoUrl.getOriginalFilename());
            Files.copy(photoUrl.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        session.setAttribute("msg", "Image Upload Sucessfully");

        return "redirect:/";
    }
}
