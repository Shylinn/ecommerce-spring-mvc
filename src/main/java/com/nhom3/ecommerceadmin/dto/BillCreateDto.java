package com.nhom3.ecommerceadmin.dto;

import com.nhom3.ecommerceadmin.models.BillDetails;
import com.nhom3.ecommerceadmin.models.Customer;
import com.nhom3.ecommerceadmin.models.Staff;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class BillCreateDto {
    public Long staff;
    public String customer;
}