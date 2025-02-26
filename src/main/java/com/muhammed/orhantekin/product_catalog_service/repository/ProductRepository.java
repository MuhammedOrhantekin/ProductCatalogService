package com.muhammed.orhantekin.product_catalog_service.repository;
import com.muhammed.orhantekin.product_catalog_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product , Long> {

    boolean existsByName(String name);

    List<Product> findByCategoriesNameIgnoreCase(String category);
}
