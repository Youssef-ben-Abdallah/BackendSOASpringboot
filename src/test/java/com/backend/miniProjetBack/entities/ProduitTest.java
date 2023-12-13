package com.backend.miniProjetBack.entities;

import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class ProduitTest extends Produit {

    @Test
    public void testProduit() {
        Long id = 1L; // You can set an appropriate ID
        String name = "Product 1";
        double price = 50.0;
        String image = "product_image.jpg";

        Produit produit = new Produit(id, name, price, image);

        // Assertions
        assertEquals(id, produit.getId());
        assertEquals(name, produit.getName());
        assertEquals(price, produit.getPrice());
        assertEquals(image, produit.getImage());
        assertNotNull(produit.toString()); // Example assertion for the toString method
    }
}