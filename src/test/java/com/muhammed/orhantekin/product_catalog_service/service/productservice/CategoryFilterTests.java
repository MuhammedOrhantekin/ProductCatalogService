package com.muhammed.orhantekin.product_catalog_service.service.productservice;

import com.muhammed.orhantekin.product_catalog_service.base.BaseTest;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.exception.BaseException;
import com.muhammed.orhantekin.product_catalog_service.model.Category;
import com.muhammed.orhantekin.product_catalog_service.model.Product;
import com.muhammed.orhantekin.product_catalog_service.util.TestDataBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class CategoryFilterTests extends BaseTest {

    // ---- GET BY CATEGORY ----

    // Başarılı Filtreleme
    @Test
    public void testGetProductsByCategory_Success() {

        Category electronics = TestDataBuilder.createCategory("Electronics");
        Product product = TestDataBuilder.createProduct(1L, "Laptop", 1500.00, "High-end laptop", "Electronics");

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(electronics));
        when(productRepository.findByCategoriesNameIgnoreCase("Electronics")).thenReturn(List.of(product));

        List<DtoProduct> products = productService.getProductsByCategory("Electronics");

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("Laptop", products.get(0).getName());
    }



    // Boş Liste - Kategoriye Ait Ürün Yok
    @Test
    public void testGetProductsByCategory_EmptyList() {

        when(categoryRepository.findByName("NonExistingCategory")).thenReturn(Optional.empty());

        BaseException exception = assertThrows(BaseException.class, () ->
                productService.getProductsByCategory("NonExistingCategory")
        );

        assertEquals("kayıt bulunamadı : Category not found with name: NonExistingCategory", exception.getMessage());
        verify(productRepository, times(0)).findByCategoriesNameIgnoreCase(anyString());
    }


    // Geçersiz Kategori - Kategori Bulunamaz
    @Test
    public void testGetProductsByCategory_InvalidCategory() {

        when(categoryRepository.findByName("InvalidCategory")).thenReturn(Optional.empty());

        BaseException exception = assertThrows(BaseException.class, () ->
                productService.getProductsByCategory("InvalidCategory")
        );

        assertEquals("kayıt bulunamadı : Category not found with name: InvalidCategory", exception.getMessage());
        verify(categoryRepository, times(1)).findByName("InvalidCategory");
        verify(productRepository, never()).findByCategoriesNameIgnoreCase(anyString());
    }


    // Null Kategori - Hata Fırlatır
    @Test
    public void testGetProductsByCategory_NullCategory() {

        BaseException exception = assertThrows(BaseException.class, () ->
                productService.getProductsByCategory(null)
        );

        assertEquals("kayıt bulunamadı : Category not found with name: null", exception.getMessage());
        verify(productRepository, never()).findByCategoriesNameIgnoreCase(anyString());
    }
}
