package com.muhammed.orhantekin.product_catalog_service.base;

import com.muhammed.orhantekin.product_catalog_service.repository.CategoryRepository;
import com.muhammed.orhantekin.product_catalog_service.repository.ProductRepository;
import com.muhammed.orhantekin.product_catalog_service.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseTest {

    @Mock
    protected ProductRepository productRepository;

    @Mock
    protected CategoryRepository categoryRepository;

    @InjectMocks
    protected ProductServiceImpl productService;
}
