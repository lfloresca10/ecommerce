package com.community.ecommerce.service;

import com.community.ecommerce.dto.ProductRequest;
import com.community.ecommerce.dto.ProductResponse;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    List<ProductResponse> fetchProducts();
    Optional<ProductResponse> fetchProduct(Long id);
    boolean deleteProduct(Long id);
    boolean updateProduct(Long id, ProductRequest productRequest);
    List<ProductResponse> searchProducts(String keyword);
    List<ProductResponse> activeProducts();
}
