package com.nhom3.ecommerceadmin.service;

import com.nhom3.ecommerceadmin.dto.BillCreateDto;
import com.nhom3.ecommerceadmin.dto.BillDto;
import com.nhom3.ecommerceadmin.models.Bill;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface BillDetailsService {

    void deleteBillDetailById(Long id);

}