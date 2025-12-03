package com.community.ecommerce.service;

import com.community.ecommerce.model.User;

import java.util.List;

public interface UserService {
    List<User> fetchUsers();
    User fetchUser(Long id);
    User createUser(User user);
    boolean deleteUser(Long id);
    boolean updateUser(User user);
}
