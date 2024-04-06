package com.nhom3.ecommerceadmin.repository;

import com.nhom3.ecommerceadmin.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
