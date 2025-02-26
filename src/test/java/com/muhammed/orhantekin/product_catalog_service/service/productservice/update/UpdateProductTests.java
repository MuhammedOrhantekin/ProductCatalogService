package com.muhammed.orhantekin.product_catalog_service.service.productservice.update;

import com.muhammed.orhantekin.product_catalog_service.base.BaseTest;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProductIU;
import com.muhammed.orhantekin.product_catalog_service.exception.BaseException;
import com.muhammed.orhantekin.product_catalog_service.model.Category;
import com.muhammed.orhantekin.product_catalog_service.model.Product;
import com.muhammed.orhantekin.product_catalog_service.util.TestDataBuilder;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class UpdateProductTests extends BaseTest {

    // Başarılı Güncelleme
    @Test
    public void testUpdateProduct_Success() {
        DtoProductIU dto = TestDataBuilder.createProductDto("Laptop Pro", 1800.0, "Updated laptop", "Electronics");
        Product existingProduct = TestDataBuilder.createProduct(1L, "Laptop", 1500.0, "High-end gaming laptop", "Electronics");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DtoProduct result = productService.updateProduct(1L, dto);

        assertNotNull(result);
        assertEquals("Laptop Pro", result.getName());
        assertEquals(1800.0, result.getPrice());
        assertEquals("Updated laptop", result.getDescription());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    // Geçersiz ID - Ürün Bulunamazsa Hata
    @Test
    public void testUpdateProduct_InvalidId() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        BaseException exception = assertThrows(BaseException.class, () ->
                productService.updateProduct(999L, TestDataBuilder.createProductDto())
        );

        assertEquals("kayıt bulunamadı : Product not found with ID: 999", exception.getMessage());
    }

    // Mevcut Kategori ile Güncelleme
    @Test
    public void testUpdateProduct_WithExistingCategory() {
        DtoProductIU dto = TestDataBuilder.createProductDto("Laptop", 1500.0, "Updated laptop", "Electronics");
        Product existingProduct = TestDataBuilder.createProduct(1L, "Laptop", 1500.0, "High-end gaming laptop", "Electronics");
        Category existingCategory = TestDataBuilder.createCategory("Electronics");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(existingCategory));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DtoProduct result = productService.updateProduct(1L, dto);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(1, result.getCategories().size());
        verify(categoryRepository, times(1)).findByName("Electronics");
    }

    // Yeni Kategori ile Güncelleme
    @Test
    public void testUpdateProduct_WithNewCategory() {
        DtoProductIU dto = TestDataBuilder.createProductDto("Gaming Laptop", 2000.0, "Powerful gaming laptop", "Gaming");
        Product existingProduct = TestDataBuilder.createProduct(1L, "Laptop", 1500.0, "High-end gaming laptop", "Electronics");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findByName("Gaming")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(TestDataBuilder.createCategory("Gaming"));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DtoProduct result = productService.updateProduct(1L, dto);

        assertNotNull(result);
        assertEquals("Gaming Laptop", result.getName());
        assertEquals(2000.0, result.getPrice());
        assertTrue(result.getCategories().stream().anyMatch(c -> c.getName().equals("Gaming")));

        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(productRepository, times(1)).save(any(Product.class));
    }

    // Kategori Olmadan Güncelleme
    @Test
    public void testUpdateProduct_WithoutCategory() {
        DtoProductIU dto = TestDataBuilder.createProductDto("Laptop", 1500.0, "Updated laptop");
        dto.setCategories(null);  // Kategori yok

        Product existingProduct = TestDataBuilder.createProduct(1L, "Laptop", 1500.0, "High-end gaming laptop", "Electronics");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DtoProduct result = productService.updateProduct(1L, dto);

        assertNotNull(result);
        assertTrue(result.getCategories().isEmpty()); // Kategori yok
    }
}
