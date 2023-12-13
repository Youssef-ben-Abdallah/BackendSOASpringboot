package com.backend.miniProjetBack.Controller;

import com.backend.miniProjetBack.entities.Facture;
import com.backend.miniProjetBack.entities.Paiement;
import com.backend.miniProjetBack.entities.User;
import com.backend.miniProjetBack.repositories.PaiementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PaiementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PaiementRepository paymentRepository;

    @InjectMocks
    private PaiementController paiementController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paiementController).build();
    }

    @Test
    void getAllPayments() throws Exception {

        List<Facture> fac =  new ArrayList<>();
        fac.add(new Facture());
        fac.add(new Facture());

        Paiement paiement1 = new Paiement(/* set your properties */
                1524 ,
                new Date() ,
                "slim",
                "15246210",
                "new Date()",
                "125",
                fac,
                new User()
        );
        Paiement paiement2 = new Paiement(/* set your properties */
                1524 ,
                new Date() ,
                "mohsen",
                "1552306569",
                "new Date()",
                "825",
                fac,
                new User()
        );
        List<Paiement> payments = Arrays.asList(paiement1, paiement2);

        when(paymentRepository.findAll()).thenReturn(payments);

        mockMvc.perform(get("/paiements/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].paymentId").value(paiement1.getPaymentId()))
                .andExpect(jsonPath("$[1].paymentId").value(paiement2.getPaymentId()));

        verify(paymentRepository, times(1)).findAll();
        verifyNoMoreInteractions(paymentRepository);
    }

    @Test
    void getPaymentById() throws Exception {
        Long paymentId = 1L;
        Paiement paiement = new Paiement();


        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(paiement));
        mockMvc.perform(get("/paiements/{id}", paymentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.paymentId").value(paiement.getPaymentId()));

        verify(paymentRepository, times(1)).findById(paymentId);
        verifyNoMoreInteractions(paymentRepository);
    }

    @Test
    void createPayment() throws Exception {
        List<Facture> fac =  new ArrayList<>();
        fac.add(new Facture());
        fac.add(new Facture());
        Paiement paiement = new Paiement(/* set your properties */
                1524 ,
                new Date() ,
                "slim",
                "15246210",
                "new Date()",
                "125",
                fac,
                new User()
        );

        when(paymentRepository.save(any(Paiement.class))).thenReturn(paiement);

        mockMvc.perform(post("/paiements/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(paiement)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.paymentId").value(paiement.getPaymentId()));

        verify(paymentRepository, times(1)).save(any(Paiement.class));
        verifyNoMoreInteractions(paymentRepository);
    }


    @Test
    void deletePayment() throws Exception {
        Long paymentId = 1L;

        mockMvc.perform(delete("/paiements/delete/{id}", paymentId))
                .andExpect(status().isOk());

        verify(paymentRepository, times(1)).deleteById(paymentId);
        verifyNoMoreInteractions(paymentRepository);
    }
}
