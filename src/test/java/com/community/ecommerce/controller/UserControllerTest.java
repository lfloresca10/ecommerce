package com.community.ecommerce.controller;

import com.community.ecommerce.dto.AddressDTO;
import com.community.ecommerce.dto.UserRequest;
import com.community.ecommerce.dto.UserResponse;
import com.community.ecommerce.model.UserRole;
import com.community.ecommerce.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    // GET /api/users
    @Test
    void shouldReturnAllUsers() throws Exception {
        List<UserResponse> mockUsers = List.of(
                new UserResponse(1L,
                        "John",
                        "Test",
                        "john@email.com",
                        "0922",
                        UserRole.CUSTOMER,
                        new AddressDTO(
                                "sante",
                                "cainta",
                                "rizal",
                                "philippines",
                                "1111")));

        Mockito.when(userService.fetchUsers()).thenReturn(mockUsers);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    // GET /api/users/{id} - found
    @Test
    void shouldReturnUserById() throws Exception {
        UserResponse user = new UserResponse(1L,
                "John",
                "Test",
                "john@email.com",
                "0922",
                UserRole.CUSTOMER,
                new AddressDTO("sante",
                        "cainta",
                        "rizal",
                        "philippines",
                        "1111"));

        Mockito.when(userService.fetchUser(1L)).thenReturn(Optional.of(user));
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email").value("john@email.com"));
    }

    // GET /api/users/{id} - not found
    @Test
    void shouldReturnNotFoundWhenUSerNotExist() throws Exception {
        Mockito.when(userService.fetchUser(10L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/users/10"))
                .andExpect(status().isNotFound());
    }

    // DELETE /api/users/{id} - success
    @Test
    void shouldDeleteUser() throws Exception {
        Mockito.when(userService.deleteUser(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserRequest userRequest = new UserRequest(
                "John",
                "Johnson",
                "john@email.com",
                "0922",
                UserRole.CUSTOMER,
                new AddressDTO("sante",
                        "cainta",
                        "rizal",
                        "philippines",
                        "1111"));
        Mockito.when(userService.updateUser(1L, userRequest)).thenReturn(true);
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated successfully"));
    }

    // POST /api/users - create
    @Test
    void shouldCreateUser() throws Exception {
        UserRequest userRequest = new UserRequest(
                "John",
                "Test",
                "john@email.com",
                "0922",
                UserRole.CUSTOMER,
                new AddressDTO("sante",
                        "cainta",
                        "rizal",
                        "philippines",
                        "1111"));
        UserResponse saved = new UserResponse(1L,
                "John",
                "Test",
                "john@email.com",
                "0922",
                UserRole.CUSTOMER,
                new AddressDTO("sante",
                        "cainta",
                        "rizal",
                        "philippines",
                        "1111"));

        Mockito.when(userService.createUser(any(UserRequest.class))).thenReturn(saved);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("john@email.com"));
    }
}
