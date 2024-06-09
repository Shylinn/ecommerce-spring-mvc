package com.nhom3.ecommerceadmin.mapper;

import com.nhom3.ecommerceadmin.dto.BillDto;
import com.nhom3.ecommerceadmin.models.Bill;

public class BillMapper {

    public static Bill mapToBill(BillDto billDto){
        Bill bill = Bill.builder()

                .staff(billDto.getStaff())
                .customer(billDto.getCustomer())

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
