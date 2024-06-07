package com.nhom3.ecommerceadmin.service;

import com.nhom3.ecommerceadmin.dto.BillCreateDto;
import com.nhom3.ecommerceadmin.dto.BillDto;
import com.nhom3.ecommerceadmin.models.Bill;

import java.util.List;

public interface BillService {
    List<BillDto> findAllBills();

    void deleteBillById(Long id);

    Bill saveBill(BillCreateDto bill);

    BillDto findBillById(Long billId);
//    Club saveClub(ClubDto clubDto);
//    ClubDto findClubById(Long clubId);
//    void updateClub(ClubDto club);
//    void delete(Long clubId);
//    List<ClubDto> searchClubs(String query);
}