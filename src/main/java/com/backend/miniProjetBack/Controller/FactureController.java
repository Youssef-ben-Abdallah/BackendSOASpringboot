package com.backend.miniProjetBack.Controller;

import com.backend.miniProjetBack.entities.Facture;
import com.backend.miniProjetBack.entities.Paiement;
import com.backend.miniProjetBack.repositories.FactureRepository;
import com.backend.miniProjetBack.repositories.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/factures")
public class FactureController  {
    @Autowired
    private  FactureRepository factureRepository;
    @Autowired
    private PaiementRepository paiementRepository;
    public FactureController(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }
    @GetMapping("/listeFactures")
    public List<Facture> listeFacture() {
        return factureRepository.findAll();
    }
    @GetMapping("/{id}")
    public Facture detailsFacture(@PathVariable Long id) {
        return factureRepository.findById(id).orElse(null);
    }

    @PostMapping("/add")
    public ResponseEntity<Facture> addFacture(@RequestBody Facture facture) {
        facture=factureRepository.save(facture);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(facture, headers, HttpStatus.OK);
    }

    @GetMapping("/byClientId/{idClient}")
    public List<Facture> getFacturesByClientId(@PathVariable Long idClient) {
        return factureRepository.findByClient_Id(idClient);
    }
    @DeleteMapping("/{id}")
    public void supprimerFacture(@PathVariable Long id) {
        factureRepository.deleteById(id);
    }
    @GetMapping("/calculateHT")
    public ResponseEntity<Double> calculateChiffreAffaireHT() {
        Double chiffreAffaireHT = factureRepository.calculateChiffreAffaireHT();
        return ResponseEntity.ok(chiffreAffaireHT);
    }
    @GetMapping("/calculateTVA")
    public ResponseEntity<Double> calculateTVA() {
        Double chiffreAffaireTVA = factureRepository.calculateTVA();
        return ResponseEntity.ok(chiffreAffaireTVA);
    }
    @GetMapping("/calculateTTC")
    public ResponseEntity<Double> calculateChiffreAffaireTTC() {
        Double chiffreAffaireTTC = factureRepository.calculateChiffreAffaireTTC();
        return ResponseEntity.ok(chiffreAffaireTTC);
    }
    @GetMapping("/totalPaye")
    public ResponseEntity<Double> calculateTotalPaye() {
        try {
            Double totalPaye = factureRepository.calculateTotalPaye();
            if (totalPaye != null) {
                System.out.println("Total Paye: " + totalPaye);
                return ResponseEntity.ok(totalPaye);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/totalNonPaye")
    public ResponseEntity<Double> calculateTotalNonPaye() {
        try {
            Double totalPaye = factureRepository.calculateTotalNonPaye();

            if (totalPaye != null) {
                System.out.println("Total Paye: " + totalPaye);
                return ResponseEntity.ok(totalPaye);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/countTotalPaye")
    public ResponseEntity<Double> countTotalPaye() {
        try {
            Double totalPaye = Double.valueOf(factureRepository.countTotalPaye());

            if (totalPaye != null) {
                System.out.println("Total Paye: " + totalPaye);
                return ResponseEntity.ok(totalPaye);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/countTotalImpaye")
    public ResponseEntity<Double> countTotalImpaye() {
        try {
            Double totalPaye = Double.valueOf(factureRepository.countTotalImpaye());
            if (totalPaye != null) {
                System.out.println("Total Paye: " + totalPaye);
                return ResponseEntity.ok(totalPaye);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/latestFour")
    public ResponseEntity<List<Facture>> getLatestFourFactures() {
        try {
            List<Facture> latestFactures = factureRepository.findLatestFourFactures();
            if (latestFactures != null && !latestFactures.isEmpty()) {
                return ResponseEntity.ok(latestFactures);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/chiffreAffaireParMois")
    public ResponseEntity<List<Double>> getChiffreAffaireParMois() {
        try {
            List<Double> chiffreAffaireParMois = factureRepository.calculateChiffreAffaireParMois();
            if (chiffreAffaireParMois != null && !chiffreAffaireParMois.isEmpty()) {
                return ResponseEntity.ok(chiffreAffaireParMois);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Facture> updateFacture(@RequestBody List<Long> factureIds) {
        List<Facture> factures = factureRepository.findAllById(factureIds);
       factures.forEach(facture -> facture.setPayer(true));
       return factureRepository.saveAll(factures );
    }



}




