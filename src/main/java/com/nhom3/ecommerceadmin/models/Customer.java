package com.nhom3.ecommerceadmin.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String address;
    private String phoneNum;
    private String email;
    private LocalDate dayOfBirth;

    @CreationTimestamp
    private LocalDateTime registeredAt;

    @Transient // Exclude from persistence
    private Double sales;
    public Double getSales() {
        return this.bills.stream().mapToDouble(Bill::getValue).sum();
    }

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Bill> bills = new ArrayList<>();

    @UpdateTimestamp
    private LocalDateTime updatedOn;
}
