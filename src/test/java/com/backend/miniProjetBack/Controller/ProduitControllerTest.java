package com.backend.miniProjetBack.Controller;

import com.backend.miniProjetBack.Controller.ProduitController;
import com.backend.miniProjetBack.entities.Facture;
import com.backend.miniProjetBack.entities.Paiement;
import com.backend.miniProjetBack.entities.Produit;
import com.backend.miniProjetBack.repositories.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProduitControllerTest {

    @Mock
    private ProduitRepository produitRepository;
    private MockMvc mockMvc;
    @InjectMocks
    private ProduitController produitController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produitController).build();
    }
    @Test
    void testGetAllProduits() throws Exception {
        // Mocking data
        Produit produit1 = new Produit(1L, "Product1", 50.0, "image1.jpg");
        Produit produit2 = new Produit(2L, "Product2", 75.0, "image2.jpg");
        List<Produit> mockProduits = Arrays.asList(produit1, produit2);

        // Mocking the repository method
        when(produitRepository.findAll()).thenReturn(mockProduits);

        // Test getAllProduits using MockMvc
        mockMvc.perform(get("/api/produits/all"))
                .andExpect(status().isOk())
                // Add more assertions based on your expected response
                .andReturn();
    }


    @Test
    void testGetFactureFromPaiement() {
        // Mocking data
        Paiement paiement = new Paiement();
        Facture facture = new Facture();
        facture.setInvoiceId(1L);
        paiement.setInvoices(Arrays.asList(facture));

        // Test getFacture() method in Paiement entity
        Facture result = paiement.getFacture();

        // Verify the result
        assertEquals(facture, result);
    }

}
