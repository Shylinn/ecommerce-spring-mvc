package com.nhom3.ecommerceadmin.mapper;

import com.nhom3.ecommerceadmin.dto.BillDto;
import com.nhom3.ecommerceadmin.dto.ProductDto;
import com.nhom3.ecommerceadmin.models.Bill;
import com.nhom3.ecommerceadmin.models.Product;

public class BillMapper {

    public static Bill mapToBill(BillDto billDto){
        Bill bill = Bill.builder()
                .id(billDto.getId())
                .createdAt(billDto.getCreatedAt())
                .staff(billDto.getStaff())
                .customer(billDto.getCustomer())
                .value(billDto.getValue())
                .updatedOn(billDto.getUpdatedOn())
                .details(billDto.getDetails())
                .build();
        return bill;
    }

    public static BillDto mapToBillDto(Bill bill){
        BillDto billDto = BillDto.builder()
                .id(bill.getId())
                .createdAt(bill.getCreatedAt())
                .staff(bill.getStaff())
                .customer(bill.getCustomer())
                .value(bill.getValue())
                .updatedOn(bill.getUpdatedOn())
                .details(bill.getDetails())
                .build();
        return billDto;
    }

}
