package com.example.banque.repository;

import com.example.banque.model.ProduitBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProduitBancaireRepository extends JpaRepository<ProduitBancaire, Long> {
    List<ProduitBancaire> findByTypeProduit_IntituleIgnoreCaseOrderByNumeroCompteAsc(String intitule);
    Optional<ProduitBancaire> findByNumeroCompteAndTypeProduit_IntituleIgnoreCase(
            String numeroCompte, String intitule);
}
