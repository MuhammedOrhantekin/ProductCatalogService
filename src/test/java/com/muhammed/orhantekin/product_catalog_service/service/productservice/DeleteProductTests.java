package com.muhammed.orhantekin.product_catalog_service.service.productservice;

import com.muhammed.orhantekin.product_catalog_service.base.BaseTest;
import com.muhammed.orhantekin.product_catalog_service.exception.BaseException;
import com.muhammed.orhantekin.product_catalog_service.model.Product;
import com.muhammed.orhantekin.product_catalog_service.util.TestDataBuilder;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DeleteProductTests extends BaseTest {

    // ---- DELETE PRODUCT ----

    // 1. Başarılı Silme
    @Test
    public void testDeleteProduct_Success() {
        Product existingProduct = TestDataBuilder.createProduct(1L, "Laptop", 1500.0, "Electronics");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        doNothing().when(productRepository).delete(existingProduct);

        assertDoesNotThrow(() -> productService.deleteProduct(1L));

        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).delete(existingProduct);
    }


    // 2. Geçersiz ID - Ürün Bulunamazsa Hata
    @Test
    public void testDeleteProduct_NotFound() {

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        BaseException exception = assertThrows(BaseException.class, () ->
                productService.deleteProduct(999L)
        );

        assertEquals("kayıt bulunamadı : Product not found with ID: 999", exception.getMessage());
        verify(productRepository, times(1)).findById(999L);
        verify(productRepository, never()).delete(any(Product.class));
    }

    // 3. Null ID - ID Null ise Hata
    @Test
    public void testDeleteProduct_NullId() {

        BaseException exception = assertThrows(BaseException.class, () ->
                productService.deleteProduct(null)
        );

        assertEquals("Geçersiz Giriş : ID must be greater than 0 and cannot be null", exception.getMessage());
        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).delete(any(Product.class));
    }

    // 4. Boş ID (0) - Hata
    @Test
    public void testDeleteProduct_EmptyId() {

        BaseException exception = assertThrows(BaseException.class, () ->
                productService.deleteProduct(0L)
        );

        assertEquals("Geçersiz Giriş : ID must be greater than 0 and cannot be null", exception.getMessage());
        verify(productRepository, never()).findById(anyLong());
        verify(productRepository, never()).delete(any(Product.class));
    }

}
