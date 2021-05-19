package com.fmi.modbd.repository;

import com.fmi.modbd.model.OrderAll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderViewAllRepository extends JpaRepository<OrderAll, Long> {
}
