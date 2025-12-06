package com.community.ecommerce.service;

import com.community.ecommerce.dto.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface OrderService {
    Optional<OrderResponse> createOrder(String userId);
}
