package com.nhom3.ecommerceadmin.service;

import com.nhom3.ecommerceadmin.dto.ClubDto;
import com.nhom3.ecommerceadmin.models.Club;

import java.util.List;

public interface ClubService {
    List<ClubDto> findAllClubs();
    Club saveClub(ClubDto clubDto);
    ClubDto findClubById(Long clubId);
    void updateClub(ClubDto club);
    void delete(Long clubId);
    List<ClubDto> searchClubs(String query);
}
