package com.backend.miniProjetBack.Controller;

import com.backend.miniProjetBack.entities.User;
import com.backend.miniProjetBack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Validate input (you may want to perform additional validation)

        // Call the login repository method
        User user = userRepository.login(email, password);

        if (user != null) {
            // If login is successful, return a JSON object with user details
            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("id", user.getId());
            userDetails.put("email", user.getEmail());
            userDetails.put("role", user.getRole()); // Assuming you have a getRole() method in your User class

            return ResponseEntity.ok().body(userDetails);
        } else {
            // If login fails, return an appropriate JSON response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid email or password"));
        }
    }
    // Endpoint to get details of a specific user by ID
    @GetMapping("/getClientById/{id}")
    public User getClientById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @GetMapping("/getRoleById/{userId}")
    public ResponseEntity<Integer> getRoleById(@PathVariable Long userId) {
        Integer role = userRepository.findRoleById(userId);

        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/top5")
    public ResponseEntity<List<Object[]>> getTop5Clients() {
        try {
            List<Object[]> topClients = userRepository.findTop5Clients();
            if (topClients != null && !topClients.isEmpty()) {
                return ResponseEntity.ok(topClients);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Use a proper logger in a production environment
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

