package com.backend.miniProjetBack.entities;


import com.backend.miniProjetBack.entities.User;
 import org.junit.jupiter.api.Test;
 import org.springframework.boot.test.context.SpringBootTest;

 import static org.junit.jupiter.api.Assertions.assertEquals;
 import static org.junit.jupiter.api.Assertions.assertNotNull;

 @SpringBootTest
 public class UserTest extends User {

     @Test
     public void testUser() {

         String nom = "John";
         String prenom = "Doe";
         String adresse = "123 Main St";
         String password = "password123";
         String email = "john.doe@example.com";
         int role = 1; // Assuming 1 represents a certain role, adjust accordingly

         User user = new User(nom, prenom, adresse, password, email, role);

         // Assertions

         assertEquals(nom, user.getNom());
         assertEquals(prenom, user.getPrenom());
         assertEquals(adresse, user.getAdresse());
         assertEquals(password, user.getPassword());
         assertEquals(email, user.getEmail());
         assertEquals(role, user.getRole());
         assertNotNull(user.toString()); // Example assertion for the toString method
     }
 }
