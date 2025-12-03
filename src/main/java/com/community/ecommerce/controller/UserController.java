package com.community.ecommerce.controller;

import com.community.ecommerce.model.User;
import com.community.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.fetchUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user =  userService.fetchUser(id);
        if(user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if(user == null) {
            return ResponseEntity.badRequest().build();
        }
        String status = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String status = userService.deleteUser(id);
        return ResponseEntity.ok(status);
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        boolean updated =  userService.updateUser(user);
        if(updated)
            return ResponseEntity.ok("User updated successfully");
        return ResponseEntity.notFound().build();
    }
}
