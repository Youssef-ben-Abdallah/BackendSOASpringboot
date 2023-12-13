package com.backend.miniProjetBack.Controller;
import com.backend.miniProjetBack.entities.Facture;
import com.backend.miniProjetBack.entities.Paiement;
import com.backend.miniProjetBack.entities.Produit;
import com.backend.miniProjetBack.entities.User;
import com.backend.miniProjetBack.repositories.FactureRepository;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class FactureControllerTest {

    @Mock
    private FactureRepository factureRepository;

    @InjectMocks
    private FactureController factureController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(factureController).build();
    }

    @Test
    void testListeFacture() throws Exception {
        // Mocking data
        Facture facture1 = new Facture(100.0, new Date(), false, null, Arrays.asList(2, 3), new User(), new Paiement(), 0, 19);
        Facture facture2 = new Facture(150.0, new Date(), true, null, Arrays.asList(1, 4), new User(), new Paiement(), 0, 19);
        List<Facture> mockFactures = Arrays.asList(facture1, facture2);

        // Mocking the repository method
        when(factureRepository.findAll()).thenReturn(mockFactures);

        // Perform the GET request
        mockMvc.perform(get("/api/factures/listeFactures")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[0].payer").value(false))
                .andExpect(jsonPath("$[1].amount").value(150.0))
                .andExpect(jsonPath("$[1].payer").value(true));
    }
    @Test
    void testDetailsFacture() throws Exception {
        // Mocking data
        Facture mockFacture = new Facture(100.0, new Date(), false, null, Arrays.asList(2, 3), new User(), new Paiement(), 0, 19);

        // Mocking the repository method
        when(factureRepository.findById(anyLong())).thenReturn(Optional.of(mockFacture));

        // Perform the GET request
        mockMvc.perform(get("/api/factures/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.payer").value(false));
    }

    @Test
    void testAjoutFacture() throws Exception {
        // Mocking data
        Facture mockFacture = new Facture(2222.0, new Date(1640304000000L), false, null, Arrays.asList(11), new User(), new Paiement(), 19, 1000);

        // Mocking the repository method with the correct argument
        when(factureRepository.save(any())).thenReturn(mockFacture);

        // Perform the POST request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/factures/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"amount\": 2222.0,"
                                + "\"dateFacture\": \"2023-12-12T00:00:00.000+00:00\","
                                + "\"payer\": false,"
                                + "\"produit\": [{\"id\": 1, \"name\": \"sqcsqsqc\", \"price\": 122, \"image\": \"sqcsqcscsqcscc\"}],"
                                + "\"qte\": [11],"
                                + "\"client\": {"
                                + "  \"id\": 1,"
                                + "  \"nom\": \"cqscc\","
                                + "  \"prenom\": \"cqscsqc\","
                                + "  \"adresse\": \"qscsqcsqcs\","
                                + "  \"password\": \"csqcsqc\","
                                + "  \"email\": \"csqcqsc\","
                                + "  \"role\": 1"
                                + "},"
                                + "\"totalHT\": 1000,"
                                + "\"tva\": 19"
                                + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(2222.0))
                .andExpect(jsonPath("$.payer").value(false))
                .andExpect(jsonPath("$.qte").isArray())
                .andExpect(jsonPath("$.qte[0]").value(11))
                .andExpect(jsonPath("$.tva").value(19))
                .andExpect(jsonPath("$.totalHT").value(1000))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);
    }




    @Test
    void testGetFacturesByClientId() throws Exception {
        // Mocking data
        Long clientId = 1L;
        List<Facture> mockFactures = Arrays.asList(
                new Facture(100.0, new Date(), false, null, Arrays.asList(2, 3), new User(), new Paiement(), 19, 19),
                new Facture(150.0, new Date(), true, null, Arrays.asList(4, 5), new User(), new Paiement(), 19, 19)
        );

        // Mocking the repository method
        when(factureRepository.findByClient_Id(clientId)).thenReturn(mockFactures);

        // Perform the GET request
        mockMvc.perform(get("/api/factures/byClientId/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))  // Assuming you expect two items in the array
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[1].amount").value(150.0))
                .andExpect(jsonPath("$[0].payer").value(false))
                .andExpect(jsonPath("$[1].payer").value(true))
                .andExpect(jsonPath("$[0].qte[0]").value(2))
                .andExpect(jsonPath("$[0].qte[1]").value(3))
                .andExpect(jsonPath("$[1].qte[0]").value(4))
                .andExpect(jsonPath("$[1].qte[1]").value(5))
                .andExpect(jsonPath("$[0].tva").value(19))
                .andExpect(jsonPath("$[1].tva").value(19));
    }

    @Test
    void testSupprimerFacture() throws Exception {
        // Mocking data
        Long factureId = 1L;

        // Perform the DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/factures/{id}", factureId))
                .andExpect(status().isOk());

        // Verify that the deleteById method was called with the correct ID
        verify(factureRepository).deleteById(factureId);
    }

    @Test
    void testCalculateChiffreAffaireHT() throws Exception {
        // Mocking data
        Double expectedChiffreAffaireHT = 5000.0;

        // Mocking the behavior of factureRepository
        when(factureRepository.calculateChiffreAffaireHT()).thenReturn(expectedChiffreAffaireHT);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/factures/calculateHT")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedChiffreAffaireHT));
    }

    @Test
    void testCalculateTVA() throws Exception {
        // Mocking data
        Double expectedTVA = 500.0;

        // Mocking the behavior of factureRepository
        when(factureRepository.calculateTVA()).thenReturn(expectedTVA);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/factures/calculateTVA")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedTVA));
    }

    @Test
    void testCalculateChiffreAffaireTTC() throws Exception {
        // Mocking data
        Double expectedChiffreAffaireTTC = 800.0;

        // Mocking the behavior of factureRepository
        when(factureRepository.calculateChiffreAffaireTTC()).thenReturn(expectedChiffreAffaireTTC);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/factures/calculateTTC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedChiffreAffaireTTC));
    }

    @Test
    void testCalculateTotalPaye() throws Exception {
        // Mocking data
        Double expectedTotalPaye = 500.0;

        // Mocking the behavior of factureRepository
        when(factureRepository.calculateTotalPaye()).thenReturn(expectedTotalPaye);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/factures/totalPaye")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedTotalPaye));
    }
    @Test
    void testCalculateTotalNonPaye() throws Exception {
        // Mocking data
        Double expectedTotalNonPaye = 300.0;

        // Mocking the behavior of factureRepository
        when(factureRepository.calculateTotalNonPaye()).thenReturn(expectedTotalNonPaye);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/factures/totalNonPaye")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedTotalNonPaye));
    }
    @Test
    void testCountTotalPaye() throws Exception {
        // Mocking data
        Double expectedCountTotalPaye = 150.0;

        // Mocking the behavior of factureRepository
        when(factureRepository.countTotalPaye()).thenReturn(expectedCountTotalPaye.longValue());

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/factures/countTotalPaye")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedCountTotalPaye));
    }
    @Test
    void testCountTotalImpaye() throws Exception {
        // Mocking data
        Double expectedCountTotalImpaye = 50.0;

        // Mocking the behavior of factureRepository
        when(factureRepository.countTotalImpaye()).thenReturn(expectedCountTotalImpaye.longValue());

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/factures/countTotalImpaye")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(expectedCountTotalImpaye));
    }
    @Test
    void testGetLatestFourFactures() throws Exception {
        // Mocking data
        Facture facture1 = new Facture(100.0, new Date(), false, null, Arrays.asList(2, 3), new User(), new Paiement(), 19, 19);
        Facture facture2 = new Facture(200.0, new Date(), false, null, Arrays.asList(4, 5), new User(), new Paiement(), 19, 19);
        Facture facture3 = new Facture(300.0, new Date(), false, null, Arrays.asList(6, 7), new User(), new Paiement(), 19, 19);
        Facture facture4 = new Facture(400.0, new Date(), false, null, Arrays.asList(8, 9), new User(), new Paiement(), 19, 19);

        List<Facture> expectedLatestFactures = Arrays.asList(facture1, facture2, facture3, facture4);

        // Mocking the behavior of factureRepository
        when(factureRepository.findLatestFourFactures()).thenReturn(expectedLatestFactures);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/factures/latestFour")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[1].amount").value(200.0))
                .andExpect(jsonPath("$[2].amount").value(300.0))
                .andExpect(jsonPath("$[3].amount").value(400.0));
        // you can test more
    }
    @Test
    void testGetChiffreAffaireParMois() throws Exception {
        // Mocking data
        List<Double> expectedChiffreAffaireParMois = Arrays.asList(1000.0, 1500.0, 800.0, 1200.0);

        // Mocking the behavior of factureRepository
        when(factureRepository.calculateChiffreAffaireParMois()).thenReturn(expectedChiffreAffaireParMois);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/factures/chiffreAffaireParMois")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0]").value(1000.0))
                .andExpect(jsonPath("$[1]").value(1500.0))
                .andExpect(jsonPath("$[2]").value(800.0))
                .andExpect(jsonPath("$[3]").value(1200.0));
        // Add similar assertions for other elements in the list if needed
    }
    @Test
    void testUpdateFacture() throws Exception {
        // Mocking data
        List<Long> factureIds = Arrays.asList(1L, 2L, 3L);

        // Mocking the behavior of factureRepository
        List<Facture> expectedFactures = Arrays.asList(
                new Facture(100.0, new Date(), false, null, Arrays.asList(2, 3), new User(), new Paiement(), 19, 19),
                new Facture(150.0, new Date(), false, null, Arrays.asList(1, 4), new User(), new Paiement(), 19, 19),
                new Facture(80.0, new Date(), false, null, Arrays.asList(5, 6), new User(), new Paiement(), 19, 19)
        );
        when(factureRepository.findAllById(factureIds)).thenReturn(expectedFactures);
        when(factureRepository.saveAll(anyList())).thenReturn(expectedFactures);

        // Perform the PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/api/factures/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1, 2, 3]"))  // Pass your JSON payload here
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].payer").value(true))
                .andExpect(jsonPath("$[1].payer").value(true))
                .andExpect(jsonPath("$[2].payer").value(true));
    }
}
