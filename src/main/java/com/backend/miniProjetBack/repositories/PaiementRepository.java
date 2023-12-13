package com.backend.miniProjetBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import com.backend.miniProjetBack.entities.Facture;
import com.backend.miniProjetBack.entities.Paiement;

import java.util.List;
import java.util.Optional;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    @Query("SELECT p FROM Paiement p WHERE p.client.id = :clientId")
    List<Paiement> findPaymentsByClientId(@Param("clientId") Long clientId);}
