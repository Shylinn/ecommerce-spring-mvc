package com.nhom3.ecommerceadmin.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BillDetailsKey implements Serializable {
    @Column(name = "bill_id")
    private Long billId;

    @Column(name = "product_id")
    private Long productId;

    @Override
    public int hashCode() {
        return Objects.hash(billId, productId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        BillDetailsKey other = (BillDetailsKey) obj;
        return Objects.equals(billId, other.billId) && Objects.equals(productId, other.productId);
    }
}
