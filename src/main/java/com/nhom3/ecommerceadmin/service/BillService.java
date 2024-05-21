package com.nhom3.ecommerceadmin.service;

import com.nhom3.ecommerceadmin.dto.BillDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BillService {
    List<BillDto> findAllBills();
//    Club saveClub(ClubDto clubDto);
//    ClubDto findClubById(Long clubId);
//    void updateClub(ClubDto club);
//    void delete(Long clubId);
//    List<ClubDto> searchClubs(String query);
}