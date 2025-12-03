package com.community.ecommerce.service;

import com.community.ecommerce.model.User;

import java.util.List;


public interface UserService {
    List<User> fetchUsers();
    User fetchUser(Long id);
    String createUser(User user);
    String deleteUser(Long id);
    String updateUser(User user);
}
