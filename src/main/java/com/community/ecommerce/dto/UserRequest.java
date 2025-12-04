package com.community.ecommerce.dto;

import com.community.ecommerce.model.UserRole;

public record UserRequest (
     String firstName,
     String lastName,
     String email,
     String phone,
     UserRole role,
     AddressDTO address
){}