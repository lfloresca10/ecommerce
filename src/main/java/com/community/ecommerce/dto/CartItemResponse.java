package com.community.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long productId;
    private Integer quantity;
}
