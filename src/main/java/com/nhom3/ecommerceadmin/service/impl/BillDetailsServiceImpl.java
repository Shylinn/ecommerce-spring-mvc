package com.nhom3.ecommerceadmin.service.impl;

import com.nhom3.ecommerceadmin.repository.BillDetailsRepository;
import com.nhom3.ecommerceadmin.service.BillDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillDetailsServiceImpl implements BillDetailsService {

    private final BillDetailsRepository billDetailsRepository;

    @Autowired
    public BillDetailsServiceImpl(BillDetailsRepository billDetailsRepository)
    {
        this.billDetailsRepository = billDetailsRepository;
    }

    @Override
    public void deleteBillDetailById(Long id) {
        billDetailsRepository.deleteById(id);
    }
}
