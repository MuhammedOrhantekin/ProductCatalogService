package com.muhammed.orhantekin.product_catalog_service.service;

import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProductIU;
import com.muhammed.orhantekin.product_catalog_service.model.Product;
import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<DtoProduct> getAllProducts();

    DtoProduct getProductById(Long id);

    DtoProduct addProduct(DtoProductIU dtoProductIU);

    DtoProduct updateProduct(Long id , DtoProductIU dtoProductIU);

    void  deleteProduct (Long id);

    List<DtoProduct> getProductsByCategory(String category);

}
