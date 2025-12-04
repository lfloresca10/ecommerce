package com.community.ecommerce.dto;

import com.community.ecommerce.model.UserRole;
import lombok.Data;

public record UserResponse (
     Long id,
     String firstName,
     String lastName,
     String email,
     String phone,
     UserRole role,
     AddressDTO address
){}
