package com.muhammed.orhantekin.product_catalog_service.repository;
import com.muhammed.orhantekin.product_catalog_service.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
