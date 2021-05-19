package com.fmi.modbd.repository;

import com.fmi.modbd.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Customer, Long> {
}
