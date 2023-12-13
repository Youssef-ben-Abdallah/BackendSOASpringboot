package com.backend.miniProjetBack.repositories;

import com.backend.miniProjetBack.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}
