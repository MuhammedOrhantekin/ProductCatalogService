package com.muhammed.orhantekin.product_catalog_service.service.impl;

import com.muhammed.orhantekin.product_catalog_service.dto.DtoProduct;
import com.muhammed.orhantekin.product_catalog_service.dto.DtoProductIU;
import com.muhammed.orhantekin.product_catalog_service.exception.BaseException;
import com.muhammed.orhantekin.product_catalog_service.exception.ErrorMessage;
import com.muhammed.orhantekin.product_catalog_service.exception.MessageType;
import com.muhammed.orhantekin.product_catalog_service.mapper.ProductMapper;
import com.muhammed.orhantekin.product_catalog_service.model.Category;
import com.muhammed.orhantekin.product_catalog_service.model.Product;
import com.muhammed.orhantekin.product_catalog_service.repository.CategoryRepository;
import com.muhammed.orhantekin.product_catalog_service.repository.ProductRepository;
import com.muhammed.orhantekin.product_catalog_service.service.IProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Tüm ürünleri listeleme
    @Override
    public List<DtoProduct> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<DtoProduct> dtoProductList = new ArrayList<>();

        for (Product product : productList) {
            dtoProductList.add(ProductMapper.toDto(product));
        }

        return dtoProductList;
    }

    // ID'ye göre ürün getirme
    @Override
    public DtoProduct getProductById(Long id) {
        if (id == null || id <= 0) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_INPUT, "ID must be greater than 0 and cannot be null"));
        }

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found with ID: " + id)));
        return ProductMapper.toDto(product);
    }


    // Yeni ürün ekleme
    @Transactional
    @Override
    public DtoProduct addProduct(DtoProductIU dtoProductIU) {
        Product product = ProductMapper.toEntity(dtoProductIU, categoryRepository);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }

    // Ürün güncelleme
    @Transactional
    @Override
    public DtoProduct updateProduct(Long id, DtoProductIU dtoProductIU) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found with ID: " + id)));

        // DTO'dan Entity dönüşümü
        Product updatedProduct = ProductMapper.toEntity(dtoProductIU, categoryRepository);
        updatedProduct.setId(existingProduct.getId());

        // Güncellenen ürünü kaydet
        Product savedProduct = productRepository.save(updatedProduct);
        return ProductMapper.toDto(savedProduct);
    }

    // Ürün silme
    @Transactional
    @Override
    public void deleteProduct(Long id) {
        if (id == null || id <= 0) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_INPUT, "ID must be greater than 0 and cannot be null"));
        }
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Product not found with ID: " + id)));
        productRepository.delete(product);
    }

    @Override
    public List<DtoProduct> getProductsByCategory(String categoryName) {
        // 1. Kategori var mı kontrol et
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Category not found with name: " + categoryName)));

        // 2. Kategori varsa, ürünleri getir
        List<Product> products = productRepository.findByCategoriesNameIgnoreCase(categoryName);
        if (products.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "No products found for category: " + categoryName));
        }

        // 3. DTO'ya dönüştür
        List<DtoProduct> dtoProductList = new ArrayList<>();
        for (Product product : products) {
            dtoProductList.add(ProductMapper.toDto(product));
        }

        return dtoProductList;
    }


}
