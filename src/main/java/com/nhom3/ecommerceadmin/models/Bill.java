package com.nhom3.ecommerceadmin.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // staff that created the bill
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Transient // Exclude from persistence
    private Double value;
    public double getValue(){
        return this.details.stream().mapToDouble(detail -> detail.getProduct().getPrice()*detail.getQuantity()).sum();
    }

    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.REMOVE)
    private Set<BillDetails> details;
}