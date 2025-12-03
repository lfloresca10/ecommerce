package com.community.ecommerce.service;

import com.community.ecommerce.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private List<User> users = new ArrayList<>();

    @Override
    public List<User> fetchUsers() {
        return users;
    }

    @Override
    public User fetchUser(Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String createUser(User user) {
        users.add(user);
        return "Added successfully";
    }

    @Override
    public String deleteUser(Long id) {
        boolean removed = users.removeIf(user -> user.getId().equals(id));
         if(removed) {
             return "Delete successfully";
         } else {
             return "User not found";
         }
    }

    @Override
    public String updateUser(User user) {
        User existingUser = users.stream()
                .filter(u -> u.getId().equals(user.getId()))
                .findFirst()
                .orElse(null);

        if(existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            return "User updated";
        } else {
            return "User not found";
        }
    }
}
