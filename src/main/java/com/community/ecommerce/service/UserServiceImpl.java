package com.community.ecommerce.service;

import com.community.ecommerce.dto.AddressDTO;
import com.community.ecommerce.dto.UserRequest;
import com.community.ecommerce.dto.UserResponse;
import com.community.ecommerce.model.Address;
import com.community.ecommerce.model.User;
import com.community.ecommerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> fetchUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::userResponseMapper)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id)
                .map(this::userResponseMapper);
    }

    @Override
    public UserResponse createUser(UserRequest user) {
        return userResponseMapper(userRepository.save(toEntity(new User(), user)));
    }

    @Override
    public boolean deleteUser(Long id) {
        if(!userRepository.existsById(id))
            return false;

        userRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateUser(Long id, UserRequest user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    userRepository.save(toEntity(existingUser, user));
                   return true;
                }).orElse(false);
    }

    private UserResponse userResponseMapper(User user) {
        return new UserResponse(
                user.getId(), user.getFirstName(), user.getLastName(),
                user.getPhone(), user.getEmail(), user.getRole(),
                user.getAddress() == null ? null :
                        new AddressDTO(user.getAddress().getStreet(),
                                user.getAddress().getCity(),
                                user.getAddress().getState(),
                                user.getAddress().getZipcode(),
                                user.getAddress().getCountry())
        );
    }

    private User toEntity(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setPhone(userRequest.phone());
        user.setEmail(userRequest.email());
        user.setRole(userRequest.role());
        if(userRequest.address() != null) {
            Address address = new Address();
            address.setCity(userRequest.address().city());
            address.setStreet(userRequest.address().street());
            address.setState(userRequest.address().state());
            address.setZipcode(userRequest.address().zipcode());
            address.setCountry(userRequest.address().country());
            user.setAddress(address);
        }
        return user;
    }
}
