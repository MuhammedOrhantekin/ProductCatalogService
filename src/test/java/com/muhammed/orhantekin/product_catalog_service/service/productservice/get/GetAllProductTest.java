package com.muhammed.orhantekin.product_catalog_service.service.productservice.get;

import com.muhammed.orhantekin.product_catalog_service.base.BaseTest;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.model.Product;
import com.muhammed.orhantekin.product_catalog_service.util.TestDataBuilder;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class GetAllProductTest extends BaseTest {

    // ---- GET ALL PRODUCTS ----
// 1. Başarılı Senaryo - Ürünler varsa
    @Test
    public void testGetAllProducts_Success() {
        List<Product> products = List.of(
                TestDataBuilder.createProduct(1L, "Laptop", 1500.0, "Electronics"),
                TestDataBuilder.createProduct(2L, "Phone", 800.0, "Electronics")
        );

        when(productRepository.findAll()).thenReturn(products);

        List<DtoProduct> result = productService.getAllProducts();

        // Doğrulamalar
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals(1500.0, result.get(0).getPrice());
        assertEquals("Phone", result.get(1).getName());
        assertEquals(800.0, result.get(1).getPrice());

        // Verify işlemi
        verify(productRepository, times(1)).findAll();
    }


    // 2. Boş Liste - Hiç ürün yoksa
    @Test
    public void testGetAllProducts_EmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        List<DtoProduct> result = productService.getAllProducts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}
