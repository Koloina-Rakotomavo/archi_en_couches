package com.example.banque.service.impl;

import com.example.banque.model.ProduitBancaire;
import com.example.banque.repository.ProduitBancaireRepository;
import com.example.banque.service.ProduitBancaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProduitBancaireServiceImpl implements ProduitBancaireService {

    private ProduitBancaireRepository produitBancaireRepository;

    @Autowired
    public ProduitBancaireServiceImpl(ProduitBancaireRepository produitBancaireRepository) {
        this.produitBancaireRepository = produitBancaireRepository;
    }

    @Override
    public List<ProduitBancaire> getAllProduitBancaire() {
        return produitBancaireRepository.findAll();
    }

    @Override
    public ProduitBancaire createProduitBancaire(ProduitBancaire produitBancaire) {
        return produitBancaireRepository.save(produitBancaire);
    }

    @Override
    public ProduitBancaire getProduitBancaireById(Long id) {
        return produitBancaireRepository.findById(id).orElseThrow();
    }

    @Override
    public ProduitBancaire updateProduitBancaire(ProduitBancaire produitBancaire) {
        return produitBancaireRepository.save(produitBancaire);
    }

    @Override
    public void deleteProduitBancaireById(Long id) {
        produitBancaireRepository.findById(id).orElseThrow();
        produitBancaireRepository.deleteById(id);
    }
}
