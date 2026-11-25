package com.example.banque.service;

import com.example.banque.model.TypeProduit;
import java.util.List;

public interface TypeProduitService {
    public List<TypeProduit> getAllTypeProduit();
    public TypeProduit createTypeProduit(TypeProduit typeProduit);
    public TypeProduit getTypeProduitById(Long id);
    public TypeProduit updateTypeProduit(TypeProduit typeProduit);
    public void deleteTypeProduitById(Long id);
}
