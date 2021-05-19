package com.fmi.modbd.repository;

import com.fmi.modbd.model.OrderAll;
import com.fmi.modbd.model.OrderBucurestiAll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderViewBucurestiAllRepository extends JpaRepository<OrderBucurestiAll, Long> {
}
