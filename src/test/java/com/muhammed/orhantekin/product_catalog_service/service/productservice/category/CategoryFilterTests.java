package com.muhammed.orhantekin.product_catalog_service.service.productservice.category;

import com.muhammed.orhantekin.product_catalog_service.base.BaseTest;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.exception.BaseException;
import com.muhammed.orhantekin.product_catalog_service.model.Category;
import com.muhammed.orhantekin.product_catalog_service.model.Product;
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

    // 1. Başarılı Filtreleme
    @Test
    public void testGetProductsByCategory_Success() {
        // Test verisi: Electronics kategorisi ve ona ait bir ürün
        Category electronics = new Category();
        electronics.setName("Electronics");

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(1500.00);
        product.setDescription("High-end laptop");
        product.setCategories(Set.of(electronics));

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(electronics));
        when(productRepository.findByCategoriesNameIgnoreCase("Electronics")).thenReturn(List.of(product));

        // Testi çalıştır
        List<DtoProduct> products = productService.getProductsByCategory("Electronics");

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("Laptop", products.get(0).getName());
    }



    // 2. Boş Liste - Kategoriye Ait Ürün Yok
    @Test
    public void testGetProductsByCategory_EmptyList() {
        // Kategori yokmuş gibi yap
        when(categoryRepository.findByName("NonExistingCategory")).thenReturn(Optional.empty());

        // Test
        BaseException exception = assertThrows(BaseException.class, () ->
                productService.getProductsByCategory("NonExistingCategory")
        );

        // Beklenen hata mesajı
        assertEquals("kayıt bulunamadı : Category not found with name: NonExistingCategory", exception.getMessage());

        // Doğrulama (ürün sorgusu hiç yapılmadığı için 0 kez çağrılmalı)
        verify(productRepository, times(0)).findByCategoriesNameIgnoreCase(anyString());
    }


    // 3. Geçersiz Kategori - Kategori Bulunamaz
    @Test
    public void testGetProductsByCategory_InvalidCategory() {
        // Kategori bulunamıyor, bu yüzden repository boş dönüyor
        when(categoryRepository.findByName("InvalidCategory")).thenReturn(Optional.empty());

        // Servis çağrıldığında exception fırlatılıyor
        BaseException exception = assertThrows(BaseException.class, () ->
                productService.getProductsByCategory("InvalidCategory")
        );

        // Beklenen hata mesajı
        assertEquals("kayıt bulunamadı : Category not found with name: InvalidCategory", exception.getMessage());

        // Doğru çağrılar kontrol ediliyor
        verify(categoryRepository, times(1)).findByName("InvalidCategory");
        verify(productRepository, never()).findByCategoriesNameIgnoreCase(anyString());
    }


    // 4. Null Kategori - Hata Fırlatır
    @Test
    public void testGetProductsByCategory_NullCategory() {
        BaseException exception = assertThrows(BaseException.class, () ->
                productService.getProductsByCategory(null)
        );

        assertEquals("kayıt bulunamadı : Category not found with name: null", exception.getMessage());
        verify(productRepository, never()).findByCategoriesNameIgnoreCase(anyString());
    }
}
