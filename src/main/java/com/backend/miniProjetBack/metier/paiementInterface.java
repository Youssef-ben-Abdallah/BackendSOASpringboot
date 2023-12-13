package com.backend.miniProjetBack.metier;

import com.backend.miniProjetBack.entities.Facture;
import com.backend.miniProjetBack.entities.Paiement;
import com.backend.miniProjetBack.entities.Produit;
import com.backend.miniProjetBack.entities.Paiement;
import org.springframework.stereotype.Service;

import java.util.List;
public interface paiementInterface {
    Paiement payer(Paiement paiement);

}
