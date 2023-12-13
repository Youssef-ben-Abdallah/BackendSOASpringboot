package com.backend.miniProjetBack.Controller;

import com.backend.miniProjetBack.entities.User;
import com.backend.miniProjetBack.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserControllerTest extends UserController {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        // Mocking data
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password");
        mockUser.setRole(0);

        // Use specific values for String parameters
        when(userRepository.login("test@example.com", "password")).thenReturn(mockUser);

        // Test login
        User loginRequest = new User();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        ResponseEntity<Map<String, Object>> response = userController.login(loginRequest);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Map<String, Object> userDetails = response.getBody();
        assertEquals(1L, userDetails.get("id"));
        assertEquals("test@example.com", userDetails.get("email"));
        assertEquals(0, userDetails.get("role")); // Assuming 'role' is an integer in your mockUser
    }


    @Test
    void testLoginFailure() {
        // Mocking a failed login
        when(userRepository.login(any().toString(), any().toString())).thenReturn(null);

        // Test login
        User loginRequest = new User();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("wrongpassword");

        ResponseEntity<Map<String, Object>> response = userController.login(loginRequest);

        // Verify the result
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Map<String, Object> errorResponse = response.getBody();
        assertEquals("Invalid email or password", errorResponse.get("error"));
    }
    @Test
    void testGetRoleByIdSuccess() {
        // Mocking data
        Long userId = 1L;
        Integer mockRole = 2;

        when(userRepository.findRoleById(userId)).thenReturn(mockRole);

        // Test getRoleById
        ResponseEntity<Integer> response = userController.getRoleById(userId);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRole, response.getBody());
    }

    @Test
    void testGetRoleByIdNotFound() {
        // Mocking a case where the role is not found
        Long userId = 1L;

        when(userRepository.findRoleById(userId)).thenReturn(null);

        // Test getRoleById
        ResponseEntity<Integer> response = userController.getRoleById(userId);

        // Verify the result
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetTop5ClientsSuccess() {
        // Mocking data
        List<Object[]> mockTopClients = Arrays.asList(
                new Object[]{1L, "User1"},
                new Object[]{2L, "User2"}
                // Add more test data as needed
        );

        when(userRepository.findTop5Clients()).thenReturn(mockTopClients);

        // Test getTop5Clients
        ResponseEntity<List<Object[]>> response = userController.getTop5Clients();

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTopClients, response.getBody());
    }

    @Test
    void testGetTop5ClientsNoContent() {
        // Mocking a case where there is no content
        when(userRepository.findTop5Clients()).thenReturn(null);

        // Test getTop5Clients
        ResponseEntity<List<Object[]>> response = userController.getTop5Clients();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetClientByIdSuccess() {
        // Mocking data
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setEmail("test@example.com");

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(mockUser));

        // Test getClientById
        User response = userController.getClientById(userId);

        // Verify the result
        assertEquals(mockUser, response);
    }

    @Test
    void testGetClientByIdNotFound() {
        // Mocking a case where the user is not found
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // Test getClientById
        User response = userController.getClientById(userId);

        // Verify the result
        assertNull(response);
    }
}