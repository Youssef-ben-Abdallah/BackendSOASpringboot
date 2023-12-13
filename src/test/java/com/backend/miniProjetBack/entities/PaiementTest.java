package com.backend.miniProjetBack.entities;

import com.backend.miniProjetBack.entities.Paiement;
import com.backend.miniProjetBack.entities.Facture;
import com.backend.miniProjetBack.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PaiementTest extends Paiement{

    @Test
    public void testPaiement() {
        Long paymentId = 1L; // You can set an appropriate ID
        double amount = 100.0;
        Date paymentDate = new Date();
        String cardHolderName = "John Doe";
        String creditCardNumber = "1234567890123456";
        String expirationDate = "12/23";
        String cvv = "123";
        User client = new User(/* initialize with appropriate values */);
        Facture facture1 = new Facture(/* initialize with appropriate values */);
        Facture facture2 = new Facture(/* initialize with appropriate values */);
        List<Facture> invoices = Arrays.asList(facture1, facture2);

        Paiement paiement = new Paiement(paymentId, amount, paymentDate, cardHolderName, creditCardNumber, expirationDate, cvv, invoices, client);

        // Assertions
        assertEquals(paymentId, paiement.getPaymentId());
        assertEquals(amount, paiement.getAmount());
        assertEquals(paymentDate, paiement.getPaymentDate());
        assertEquals(cardHolderName, paiement.getCardHolderName());
        assertEquals(creditCardNumber, paiement.getCreditCardNumber());
        assertEquals(expirationDate, paiement.getExpirationDate());
        assertEquals(cvv, paiement.getCvv());
        assertEquals(client, paiement.getClient());
        assertEquals(invoices, paiement.getInvoices());
        assertNotNull(paiement.toString()); // Example assertion for the toString method
        assertNotNull(paiement.getFacture());
    }
}
