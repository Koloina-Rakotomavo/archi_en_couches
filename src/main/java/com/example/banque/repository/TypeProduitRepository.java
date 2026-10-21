package com.example.banque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.banque.model.TypeProduit;

import java.util.ArrayList;

public interface TypeProduitRepository extends JpaRepository<TypeProduit, Long> {
    ArrayList<TypeProduit> findByIntitule(String intitule);
    ArrayList<TypeProduit> findByIntituleLike(String typeProduit);
    ArrayList<TypeProduit> findByIntituleContains(String typeProduit);
    ArrayList<TypeProduit> findFirst3ByOrderByIdDesc();
    ArrayList<TypeProduit> findByTauxInteretAgiosGreaterThanEqualOrderByTauxInteretAgiosAsc(float taux);
}