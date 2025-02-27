package com.muhammed.orhantekin.product_catalog_service.util;

import com.muhammed.orhantekin.product_catalog_service.dto.DtoCategory;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProductIU;
import com.muhammed.orhantekin.product_catalog_service.model.Category;
import com.muhammed.orhantekin.product_catalog_service.model.Product;

import java.util.HashSet;
import java.util.Set;


public class TestDataBuilder {


    // Varsayılan DTO Üretici
    public static DtoProductIU createProductDto() {
        return createProductDto("Laptop", 1500.0, "High-end gaming laptop", "Electronics");
    }


    // Özel DTO Üretici
    public static DtoProductIU createProductDto(String name, double price, String description, String... categoryNames) {
        DtoProductIU dto = new DtoProductIU();
        dto.setName(name);
        dto.setPrice(price);
        dto.setDescription(description);

        Set<DtoCategory> categories = new HashSet<>();
        if (categoryNames != null) {
            for (String categoryName : categoryNames) {
                categories.add(new DtoCategory(categoryName));
            }
        }
        dto.setCategories(categories);

        return dto;
    }



    // Varsayılan Product Entity Üretici
    public static Product createProduct() {
        return createProduct(1L, "Laptop", 1500.0, "High-end gaming laptop", "Electronics");
    }



    // Özel Product Entity Üretici
    public static Product createProduct(Long id, String name, double price, String description, String... categoryNames) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);

        Set<Category> categories = new HashSet<>();
        if (categoryNames != null) {
            for (String categoryName : categoryNames) {
                categories.add(createCategory(categoryName));
            }
        }
        product.setCategories(categories);

        return product;
    }


    // Kategori Üretici
    public static Category createCategory(String name) {
        Category category = new Category();
        category.setId(1L);
        category.setName(name);
        return category;
    }
}
