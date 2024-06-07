package com.nhom3.ecommerceadmin.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bill_details")
public class BillDetails {
    @EmbeddedId
    private BillDetailsKey id;

    @ManyToOne
    @MapsId("billId")
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Transient
    private Double totalPrice; // This field is not persisted in the database

    public void calculateTotalPrice() {
        if (product != null && product.getPrice() != null) {
            this.totalPrice = this.quantity * product.getPrice() * 1.00;
        } else {
            this.totalPrice = 0.0; // Or handle this case as needed
        }
    }

}