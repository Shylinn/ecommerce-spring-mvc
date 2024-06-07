package com.nhom3.ecommerceadmin.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private Double value;

    public Double getTotalAmount() {
        return details.stream()
                .mapToDouble(BillDetails::getTotalPrice)
                .sum();
    }

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "bill")
    private Set<BillDetails> details;

    @Transient
    private String customerName;

    @Transient
    private String staffName;

}
