package com.backend.miniProjetBack.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FactureTest extends Facture {
    @Test
    public void testFacture() {
        double amount = 100.0;
        Date dateFacture = new Date();
        Boolean payer = false;
        Produit produit1 = new Produit(/* initialize with appropriate values */);
        Produit produit2 = new Produit(/* initialize with appropriate values */);
        List<Produit> produits = Arrays.asList(produit1, produit2);
        List<Integer> qte = Arrays.asList(2, 3);
        User client = new User(/* initialize with appropriate values */);
        Paiement payment = new Paiement(/* initialize with appropriate values */);
        int tva = 19;
        int totalHT = 200;

        Facture facture = new Facture(amount, dateFacture, payer, produits, qte, client, payment, tva, totalHT);

        // Assertions
        assertEquals(amount, facture.getAmount());
        assertEquals(dateFacture, facture.getDateFacture());
        assertEquals(payer, facture.getPayer());
        assertEquals(produits, facture.getProduit());
        assertEquals(qte, facture.getQte());
        assertEquals(client, facture.getClient());
        assertEquals(payment, facture.getPayment());
        assertEquals(totalHT, facture.getTotalHT());
        assertEquals(tva, facture.getTva());


        // Example boolean assertion
        assertTrue(facture.getTotalHT() >= 0);
    }
}