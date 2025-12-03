package com.community.ecommerce.service;

import com.community.ecommerce.model.User;
import com.community.ecommerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<User> fetchUsers() {
        return userRepository.findAll();
    }

    @Override
    public User fetchUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        if(!userRepository.existsById(id))
            return false;

        userRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        return userRepository.findById(user.getId())
                .map(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    userRepository.save(existingUser);
                   return true;
                }).orElse(false);
    }
}
