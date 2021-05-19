package com.fmi.modbd.repository;

import com.fmi.modbd.model.OrderAll;
import com.fmi.modbd.model.OrderBrasovAll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderViewBrasovAllRepository extends JpaRepository<OrderBrasovAll, Long> {
}
