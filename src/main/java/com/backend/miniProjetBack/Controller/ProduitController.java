package com.backend.miniProjetBack.Controller;

import com.backend.miniProjetBack.entities.Produit;
import com.backend.miniProjetBack.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitRepository produitImpl;
    @GetMapping("/all")
    public List<Produit> getAllProduits() {
        return produitImpl.findAll();
    }

    @PostMapping("/add/")
    public Produit addProduit(@RequestBody Produit produit) {
        return produitImpl.save(produit);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduit(@PathVariable Long id) {
        produitImpl.deleteById(id);
    }
    @PutMapping("/modify/{id}")
    public Produit modifyProduit(@PathVariable Long id, @RequestBody Produit modifiedProduit) {
        Optional<Produit> existingProduit = produitImpl.findById(id);

        if (existingProduit.isPresent()) {
            Produit currentProduit = existingProduit.get();
            currentProduit.setName(modifiedProduit.getName());
            currentProduit.setPrice(modifiedProduit.getPrice());
            return produitImpl.save(currentProduit);
        } else {
            return null;
        }
    }
    @GetMapping("/details/{id}")
    public Optional<Produit> getProduitDetails(@PathVariable Long id) {
        return produitImpl.findById(id);
    }
}