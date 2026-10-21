package com.example.banque.model;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.banque.model.TypeProduit;

public interface TypeProduitRepository extends JpaRepository<TypeProduit, Long> {
}