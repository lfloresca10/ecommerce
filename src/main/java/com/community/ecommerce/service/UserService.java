package com.community.ecommerce.service;

import com.community.ecommerce.dto.UserRequest;
import com.community.ecommerce.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponse> fetchUsers();
    Optional<UserResponse> fetchUser(Long id);
    UserResponse createUser(UserRequest user);
    boolean deleteUser(Long id);
    boolean updateUser(Long id, UserRequest user);
}
