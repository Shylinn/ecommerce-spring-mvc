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
public class BillDto {
    private Long id;

    private LocalDateTime createdAt;

    private Staff staff;

    @NotEmpty(message = "Nhập tên khách hàng")
    private Customer customer;

    @Transient // Exclude from persistence
    private Double value;
    public double getValue(){
        return this.details.stream().mapToDouble(detail -> detail.getProduct().getPrice()*detail.getQuantity()).sum();
    }

    private LocalDateTime updatedOn;

    private Set<BillDetails> details;
}