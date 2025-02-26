package com.muhammed.orhantekin.product_catalog_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muhammed.orhantekin.product_catalog_service.controller.impl.ProductControllerImpl;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProductIU;
import com.muhammed.orhantekin.product_catalog_service.exception.BaseException;
import com.muhammed.orhantekin.product_catalog_service.exception.ErrorMessage;
import com.muhammed.orhantekin.product_catalog_service.exception.MessageType;
import com.muhammed.orhantekin.product_catalog_service.handler.GlobalExceptionHandler;
import com.muhammed.orhantekin.product_catalog_service.service.IProductService;
import com.muhammed.orhantekin.product_catalog_service.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductControllerImpl.class)
@Import(GlobalExceptionHandler.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private DtoProductIU dtoProductIU;
    private DtoProduct dtoProduct;

    @BeforeEach
    void setUp() {
        dtoProductIU = TestDataBuilder.createProductDto();
        dtoProduct = new DtoProduct(1L, 1500.0, "Laptop", "High-end gaming laptop", Collections.emptySet());
    }

    // ---- GET ALL PRODUCTS ----

    @Test
    public void testGetAllProducts_Success() throws Exception {
        when(productService.getAllProducts()).thenReturn(List.of(dtoProduct));

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[0].price").value(1500.0));
    }

    @Test
    public void testGetAllProducts_EmptyList() throws Exception {
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    // ---- GET PRODUCT BY ID ----

    @Test
    public void testGetProductById_Success() throws Exception {
        when(productService.getProductById(1L)).thenReturn(dtoProduct);

        mockMvc.perform(get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(1500.0));
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById(999L)).thenThrow(new BaseException(
                new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")
        ));

        mockMvc.perform(get("/products/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exceptionn.message").value("kayıt bulunamadı : Product not found"));
    }


    // ---- ADD PRODUCT ----

    @Test
    public void testAddProduct_Success() throws Exception {
        when(productService.addProduct(any(DtoProductIU.class))).thenReturn(dtoProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoProductIU)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    public void testAddProduct_InvalidInput() throws Exception {
        DtoProductIU invalidDto = new DtoProductIU("", 0.0, "", Collections.emptySet());

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // ---- UPDATE PRODUCT ----

    @Test
    public void testUpdateProduct_Success() throws Exception {
        when(productService.updateProduct(eq(1L), any(DtoProductIU.class))).thenReturn(dtoProduct);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoProductIU)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    public void testUpdateProduct_NotFound() throws Exception {
        when(productService.updateProduct(eq(999L), any(DtoProductIU.class))).thenThrow(new BaseException(
                new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")
        ));

        mockMvc.perform(put("/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoProductIU)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exceptionn.message").value("kayıt bulunamadı : Product not found"));
    }

    // ---- DELETE PRODUCT ----

    @Test
    public void testDeleteProduct_Success() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully."));
    }

    @Test
    public void testDeleteProduct_NotFound() throws Exception {
        doThrow(new BaseException(
                new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found")
        )).when(productService).deleteProduct(999L);

        mockMvc.perform(delete("/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exceptionn.message").value("kayıt bulunamadı : Product not found"));
    }

    // ---- FILTER BY CATEGORY ----

    @Test
    public void testGetProductsByCategory_Success() throws Exception {
        when(productService.getProductsByCategory("Electronics")).thenReturn(List.of(dtoProduct));

        mockMvc.perform(get("/products/categoryFilter")
                        .param("category", "Electronics")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Laptop"));
    }

    @Test
    public void testGetProductsByCategory_NotFound() throws Exception {
        when(productService.getProductsByCategory("Unknown")).thenThrow(new BaseException(
                new ErrorMessage(MessageType.NO_RECORD_EXIST, "Category not found")
        ));

        mockMvc.perform(get("/products/categoryFilter")
                        .param("category", "Unknown")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.exceptionn.message").value("kayıt bulunamadı : Category not found"));
    }
}
