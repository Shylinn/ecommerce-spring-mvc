package com.nhom3.ecommerceadmin.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BillCreateDto {
    public Long staff;
    public String customer;
}