package com.fmi.modbd.repository;

import com.fmi.modbd.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
