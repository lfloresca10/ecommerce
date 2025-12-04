package com.community.ecommerce.service;

import com.community.ecommerce.dto.ProductRequest;
import com.community.ecommerce.dto.ProductResponse;
import com.community.ecommerce.model.Product;
import com.community.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        return productResponseMapper(
                productRepository.save(toEntity(new Product(), productRequest)));
    }

    @Override
    public List<ProductResponse> fetchProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::productResponseMapper)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<ProductResponse> fetchProduct(Long id) {
        return productRepository.findById(id)
                .map(this::productResponseMapper);
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword)
                .stream()
                .map(this::productResponseMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> activeProducts() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::productResponseMapper)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteProduct(Long id) {
        if(!productRepository.existsById(id))
            return false;
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    productRepository.save(toEntity(existingProduct, productRequest));
                    return true;
                }).orElse(false);
    }

    private Product toEntity(Product product, ProductRequest productRequest) {
        System.out.println("test " + productRequest.name());
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setDescription(productRequest.description());
        product.setStockQuantity(productRequest.stockQuantity());
        product.setCategory(productRequest.category());
        product.setImageUrl(productRequest.imageUrl());
        product.setActive(true);

        return product;
    }

    private ProductResponse productResponseMapper(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory(),
                product.getImageUrl(),
                product.getActive()
        );
    }

}
