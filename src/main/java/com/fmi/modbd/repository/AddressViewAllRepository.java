package com.fmi.modbd.repository;

import com.fmi.modbd.model.AddressAll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressViewAllRepository extends JpaRepository<AddressAll, Long> {
}
