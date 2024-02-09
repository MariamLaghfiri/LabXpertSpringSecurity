package com.simplon.labxpert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplon.labxpert.model.dto.UserDTO;
import com.simplon.labxpert.model.enums.EUserRole;
import com.simplon.labxpert.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.when;
/**
 * Tests for the UserController class.
 * These tests use MockMvc to simulate HTTP requests and verify the behavior of UserController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    private UserDTO newUser;
    /**
     * Setup method to initialize common objects before each test.
     *
     */
    @BeforeEach
    void setUp() {
        newUser = new UserDTO();
        newUser.setUserID(1L);
        newUser.setEmail("test@example.com");
        newUser.setIsEmailVerified(true);
        newUser.setUsername("testUser");
        newUser.setPassword("TestPassword123!");
        newUser.setUserRole(EUserRole.TECHNICIAN);
        newUser.setPersonalInfo("Some personal info");
    }
    /**
     * Test case for retrieving all users.
     * Verifies that the endpoint returns a list of users and interacts with UserService appropriately.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void getAllUsers() throws Exception {
        List<UserDTO> fakeUsers = Collections.singletonList(newUser);
        when(userService.getAllUsers()).thenReturn(fakeUsers);
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fakeUsers)));
        verify(userService, times(1)).getAllUsers();
    }


    /**
     * Tests the retrieval of a user by ID.
     * Verifies that the endpoint returns a user based on the provided ID,
     * and confirms interaction with the UserService.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(newUser);
        mockMvc.perform(get("/api/v1/users/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(newUser)));
        verify(userService, times(1)).getUserById(1L);
    }
    /**
     * Test case for creating a new user.
     * Verifies that the endpoint creates a user and interacts with UserService appropriately.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void createUser() throws Exception {
        when(userService.createUser(any())).thenReturn(newUser);
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(newUser)));
        verify(userService, times(1)).createUser(any());
    }
    /**
     * Test case for updating a user.
     * Verifies that the endpoint updates a user.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void updateUser() throws Exception {
        newUser.setEmail("updated@example.com");
        newUser.setPersonalInfo("Updated personal info");
        when(userService.updateUser(any(), anyLong())).thenReturn(newUser);
        mockMvc.perform(put("/api/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(newUser))).andDo(print());
        verify(userService, times(1)).updateUser(any(), anyLong());
    }
    /**
     * Test case for deleting a user.
     * Verifies that the endpoint deletes a user and interacts with UserService appropriately.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", 1L))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUser(1L);
    }
}