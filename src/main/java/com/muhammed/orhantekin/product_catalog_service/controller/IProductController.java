package com.muhammed.orhantekin.product_catalog_service.controller;

import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProductIU;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductController {


    public List<DtoProduct> getAllProducts();

    public DtoProduct getProductById(Long id);

    public DtoProduct addProduct(DtoProductIU dtoProductIU);

    public DtoProduct updateProduct(Long id, DtoProductIU dtoProductIU);

    public ResponseEntity<String> deleteProduct(Long id);

    public List<DtoProduct>  getProductByCategory(String category);
}
