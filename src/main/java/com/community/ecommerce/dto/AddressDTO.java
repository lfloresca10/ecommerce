package com.community.ecommerce.dto;

public record AddressDTO(
     String street,
     String city,
     String state,
     String country,
     String zipcode)
{ }
