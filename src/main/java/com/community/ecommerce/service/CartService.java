package com.community.ecommerce.service;

import com.community.ecommerce.dto.CartItemRequest;
import com.community.ecommerce.model.CartItem;

import java.util.List;

public interface CartService {
    List<CartItem> getCart(String userId);

    boolean addToCart(String userId, CartItemRequest request);

    boolean deleteItemFromCart(String userId, Long productId);

    void clearCart(String userId);
}
