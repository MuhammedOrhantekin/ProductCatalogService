package com.muhammed.orhantekin.product_catalog_service.controller.impl;

import com.muhammed.orhantekin.product_catalog_service.controller.IProductController;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProductIU;

import com.muhammed.orhantekin.product_catalog_service.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductControllerImpl implements IProductController {


    private final IProductService productService;

    public ProductControllerImpl(IProductService productService) {
        this.productService = productService;
    }


    //Tüm Ürünleri listeleme
    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @GetMapping
    @Override
    public List<DtoProduct> getAllProducts() {
        return productService.getAllProducts();
    }


    //Belirli bir ürünü getirme
    @Operation(summary = "Get product by ID", description = "Returns a product by its ID")
    @GetMapping("{id}")
    @Override
    public DtoProduct getProductById(@PathVariable  Long id ){
        return productService.getProductById(id);
    }


    //Yeni ürün ekleme
    @Operation(summary = "Create a new product", description = "Adds a new product to the catalog")
    @PostMapping
    @Override
    public DtoProduct addProduct(@Valid @RequestBody DtoProductIU dtoProductIU) {
        return productService.addProduct(dtoProductIU);
    }


    //Ürünü güncelleme
    @Operation(summary = "Update product details", description = "Updates the details of an existing product")
    @PutMapping("{id}")
    @Override
    public DtoProduct updateProduct(@PathVariable Long id, @Valid @RequestBody DtoProductIU dtoProductIU) {
        return productService.updateProduct(id, dtoProductIU);
    }


    //Ürün Silme
    @Operation(summary = "Delete a product", description = "Removes a product from the catalog")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully.");
    }


    //Kategorilere göre filtreleme
    @Operation(summary = "Filter products by category", description = "Retrieves a list of products that belong to a specific category")
    @GetMapping("/categoryFilter")
    @Override
    public List<DtoProduct> getProductByCategory(@RequestParam String category) {
        return productService.getProductsByCategory(category);
    }
}
