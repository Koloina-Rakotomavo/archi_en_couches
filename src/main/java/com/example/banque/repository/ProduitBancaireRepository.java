package com.example.banque.repository;

import com.example.banque.model.ProduitBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitBancaireRepository extends JpaRepository<ProduitBancaire, Long> {
}
