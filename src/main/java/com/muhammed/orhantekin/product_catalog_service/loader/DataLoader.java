/*package com.muhammed.orhantekin.product_catalog_service.loader;

import com.muhammed.orhantekin.product_catalog_service.model.Product;
import com.muhammed.orhantekin.product_catalog_service.model.Category;
import com.muhammed.orhantekin.product_catalog_service.repository.ProductRepository;
import com.muhammed.orhantekin.product_catalog_service.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository, CategoryRepository categoryRepository) {
        return args -> {

            // Kategori ekleme
            Category electronics = categoryRepository.findByName("Electronics")
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName("Electronics");
                        return categoryRepository.save(newCategory);
                    });

            Category books = categoryRepository.findByName("Books")
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName("Books");
                        return categoryRepository.save(newCategory);
                    });

            // Ürün ekleme
            if (!productRepository.existsByName("Laptop")) {
                Product laptop = new Product();
                laptop.setName("Laptop");
                laptop.setPrice(1500.00);
                laptop.setDescription("High-performance laptop");

                Set<Category> laptopCategories = new HashSet<>();
                laptopCategories.add(electronics);
                laptop.setCategories(laptopCategories);

                productRepository.save(laptop);
            }

            if (!productRepository.existsByName("Novel")) {
                Product novel = new Product();
                novel.setName("Novel");
                novel.setPrice(20.00);
                novel.setDescription("Fictional novel");

                Set<Category> novelCategories = new HashSet<>();
                novelCategories.add(books);
                novel.setCategories(novelCategories);

                productRepository.save(novel);
            }

            System.out.println("Test verileri başarıyla eklendi!");
        };
    }
}*/
