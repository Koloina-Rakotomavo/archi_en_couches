package com.example.banque.service;

import com.example.banque.model.ProduitBancaire;
import java.util.List;

public interface ProduitBancaireService {
    public List<ProduitBancaire> getAllProduitBancaire();
    public ProduitBancaire createProduitBancaire(ProduitBancaire produitBancaire);
    public ProduitBancaire getProduitBancaireById(Long id);
    public ProduitBancaire updateProduitBancaire(ProduitBancaire produitBancaire);
    public void deleteProduitBancaireById(Long id);
}
