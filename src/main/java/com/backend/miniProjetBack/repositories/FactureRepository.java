package com.backend.miniProjetBack.repositories;

import com.backend.miniProjetBack.entities.Facture;
import com.backend.miniProjetBack.entities.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface FactureRepository extends JpaRepository<Facture, Long> {
    default Optional<Facture> detailsFacture(Long id) {
        return findById(id);
    }

    List<Facture> findByClient_Id(Long idClient);
    @Query("SELECT COALESCE(SUM(f.totalHT), 0) FROM Facture f")
    Double calculateChiffreAffaireHT();

    @Query("SELECT SUM(tva) FROM Facture")
    public Double calculateTVA();

    @Query("SELECT SUM(f.totalHT + COALESCE(f.tva, 0)) FROM Facture f")
    double calculateChiffreAffaireTTC();

    @Query("SELECT SUM(amount) FROM Facture WHERE payer = true")
    Double calculateTotalPaye();

    @Query("SELECT SUM(amount) FROM Facture WHERE payer = false")
    Double calculateTotalNonPaye();
    @Query("SELECT COUNT(*) FROM Facture WHERE payer = true")
    public Long countTotalPaye();

    @Query("SELECT COUNT(*) FROM Facture WHERE payer = false")
    public Long countTotalImpaye();
    @Query("SELECT f FROM Facture f ORDER BY f.dateFacture ASC")
    List<Facture> findLatestFourFactures();
    // chiffre d'affaire par mois
    @Query("SELECT SUM(amount) FROM Facture WHERE YEAR(dateFacture) = YEAR(CURRENT_DATE) GROUP BY MONTH(dateFacture)")
    public List<Double> calculateChiffreAffaireParMois();

    @Modifying
    @Query("UPDATE Facture f SET f.payer = true WHERE f.invoiceId = :factureId")
    void markFactureAsPaid(@Param("factureId") Long factureId);


}
