package com.muhammed.orhantekin.product_catalog_service.mapper;

import com.muhammed.orhantekin.product_catalog_service.dto.DtoCategory;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProductIU;
import com.muhammed.orhantekin.product_catalog_service.model.Category;
import com.muhammed.orhantekin.product_catalog_service.model.Product;
import com.muhammed.orhantekin.product_catalog_service.repository.CategoryRepository;

import java.util.HashSet;
import java.util.Set;

public class ProductMapper {

    // Entity -> DTO (Product -> DtoProduct)
    public static DtoProduct toDto(Product product) {
        if (product == null) {
            return null;
        }

        DtoProduct dto = new DtoProduct();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());

        // Kategorileri dönüştürme (null kontrolü ile)
        Set<DtoCategory> dtoCategories = new HashSet<>();
        if (product.getCategories() != null) {
            for (Category category : product.getCategories()) {
                if (category != null && category.getName() != null) {
                    DtoCategory dtoCategory = new DtoCategory();
                    dtoCategory.setName(category.getName());
                    dtoCategories.add(dtoCategory);
                }
            }
        }
        dto.setCategories(dtoCategories);

        return dto;
    }


    // DTO -> Entity (DtoProductIU -> Product)
    public static Product toEntity(DtoProductIU dto, CategoryRepository categoryRepository) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());

        // Kategori kontrolü
        Set<Category> categories = new HashSet<>();
        if (dto.getCategories() != null) {
            for (DtoCategory dtoCategory : dto.getCategories()) {
                Category category = categoryRepository.findByName(dtoCategory.getName())
                        .orElseGet(() -> {
                            Category newCategory = new Category();
                            newCategory.setName(dtoCategory.getName());
                            return categoryRepository.save(newCategory);
                        });

                categories.add(category);
            }
        }
        product.setCategories(categories);

        return product;
    }

}
