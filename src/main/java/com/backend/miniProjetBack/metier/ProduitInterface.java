package com.backend.miniProjetBack.metier;

import com.backend.miniProjetBack.entities.Produit;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ProduitInterface {
    List<Produit> listeProduits();
}
