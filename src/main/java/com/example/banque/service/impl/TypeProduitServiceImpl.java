package com.example.banque.service.impl;

import com.example.banque.model.TypeProduit;
import com.example.banque.repository.TypeProduitRepository;
import com.example.banque.service.TypeProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TypeProduitServiceImpl implements TypeProduitService {

    private TypeProduitRepository typeProduitRepository;

    @Autowired
    public TypeProduitServiceImpl(TypeProduitRepository typeProduitRepository) {
        this.typeProduitRepository = typeProduitRepository;
    }

    @Override
    public List<TypeProduit> getAllTypeProduit() {
        return typeProduitRepository.findAll();
    }

    @Override
    public TypeProduit createTypeProduit(TypeProduit typeProduit) {
        return typeProduitRepository.save(typeProduit);
    }

    @Override
    public TypeProduit getTypeProduitById(Long id) {
        return typeProduitRepository.findById(id).orElseThrow();
    }

    @Override
    public TypeProduit updateTypeProduit(TypeProduit typeProduit) {
        return typeProduitRepository.save(typeProduit);
    }

    @Override
    public void deleteTypeProduitById(Long id) {
        typeProduitRepository.findById(id).orElseThrow();
        typeProduitRepository.deleteById(id);
    }
}
