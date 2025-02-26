package com.muhammed.orhantekin.product_catalog_service.service.productservice.get;

import com.muhammed.orhantekin.product_catalog_service.base.BaseTest;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.exception.BaseException;
import com.muhammed.orhantekin.product_catalog_service.model.Product;
import com.muhammed.orhantekin.product_catalog_service.util.TestDataBuilder;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class GetProductByIdTest extends BaseTest {

    // ---- GET PRODUCT BY ID ----

    // 1. Başarılı Senaryo - Geçerli ID ile Ürün Bulunur
    @Test
    public void testGetProductById_Success() {
        Product product = TestDataBuilder.createProduct(1L, "Laptop", 1500.0, "Electronics");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        DtoProduct result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Laptop", result.getName());
        assertEquals(1500.0, result.getPrice());
    }

    // 2. Geçersiz ID - Ürün Bulunamazsa Hata
    @Test
    public void testGetProductById_NotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        BaseException exception = assertThrows(BaseException.class, () ->
                productService.getProductById(999L)
        );

        assertTrue(exception.getMessage().contains("Product not found"));
    }

    // Null ID ile test
    @Test
    public void testGetProductById_NullId() {
        BaseException exception = assertThrows(BaseException.class, () ->
                productService.getProductById(null)
        );
        assertTrue(exception.getMessage().contains("ID must be greater than 0 and cannot be null"));
    }

    // 0 ID ile test
    @Test
    public void testGetProductById_EmptyId() {
        BaseException exception = assertThrows(BaseException.class, () ->
                productService.getProductById(0L)
        );
        assertTrue(exception.getMessage().contains("ID must be greater than 0 and cannot be null"));
    }
}
