package com.nhom3.ecommerceadmin.service;

import com.nhom3.ecommerceadmin.dto.BillCreateDto;
import com.nhom3.ecommerceadmin.dto.BillDto;
import com.nhom3.ecommerceadmin.models.Bill;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface BillService {
    List<BillDto> findAllBills();

    void deleteBillById(Long id);

    Bill saveBill(BillCreateDto bill);

    ByteArrayInputStream exportBillToPdf(Long billId);

    BillDto findBillById(Long billId);
    void updateBillDetails(BillDto updatedBillDto);
//    Club saveClub(ClubDto clubDto);
//    ClubDto findClubById(Long clubId);
//    void delete(Long clubId);
//    List<ClubDto> searchClubs(String query);
}