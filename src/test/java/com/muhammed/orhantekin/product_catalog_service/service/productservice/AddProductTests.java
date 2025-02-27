package com.muhammed.orhantekin.product_catalog_service.service.productservice;

import com.muhammed.orhantekin.product_catalog_service.base.BaseTest;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProductIU;
import com.muhammed.orhantekin.product_catalog_service.model.Category;
import com.muhammed.orhantekin.product_catalog_service.model.Product;
import com.muhammed.orhantekin.product_catalog_service.util.TestDataBuilder;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class AddProductTests extends BaseTest {

    // ---- ADD PRODUCT ----

    // Başarılı Ürün Ekleme testi
    @Test
    public void testAddProduct_Success() {
        DtoProductIU dto = TestDataBuilder.createProductDto();
        Product savedProduct = TestDataBuilder.createProduct(1L, "Laptop", 1500.0, "High-end gaming laptop", "Electronics");

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        DtoProduct result = productService.addProduct(dto);

        assertNotNull(result);
        assertEquals(savedProduct.getName(), result.getName());
        assertEquals(savedProduct.getPrice(), result.getPrice());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    // Mevcut Kategori ile Ekleme testi
    @Test
    public void testAddProduct_WithExistingCategory() {
        DtoProductIU dto = TestDataBuilder.createProductDto("Laptop", 1500.0, "High-end gaming laptop", "Electronics");
        Category existingCategory = TestDataBuilder.createCategory("Electronics");

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(existingCategory));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DtoProduct result = productService.addProduct(dto);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(1, result.getCategories().size());

        verify(categoryRepository, times(1)).findByName("Electronics");
    }

    // Yeni Kategori ile Ekleme testi
    @Test
    public void testAddProduct_WithNewCategory() {
        DtoProductIU dto = TestDataBuilder.createProductDto("Laptop", 1500.0, "High-end gaming laptop", "Gaming");

        when(categoryRepository.findByName("Gaming")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(TestDataBuilder.createCategory("Gaming"));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DtoProduct result = productService.addProduct(dto);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(1, result.getCategories().size());
        assertTrue(result.getCategories().stream().anyMatch(c -> c.getName().equals("Gaming")));

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

}
