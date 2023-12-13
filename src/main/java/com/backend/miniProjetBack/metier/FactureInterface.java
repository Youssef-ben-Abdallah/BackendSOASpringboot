package com.backend.miniProjetBack.metier;

import com.backend.miniProjetBack.entities.Facture;
import org.springframework.stereotype.Service;

import java.util.List;
public interface FactureInterface {
    List<Facture> listeFacture();
    Facture detailsFacture(Long id);
    Facture ajoutFacture(Facture factureDTO);
    Facture supprimerFacture(Long id);
    Facture getFactureById(Long id);
    List<Facture> findByClient_Id(Long idClient);
     void updateFacture(Facture facture) ;

}
