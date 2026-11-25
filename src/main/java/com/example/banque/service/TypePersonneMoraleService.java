package com.example.banque.service;

import com.example.banque.model.TypePersonneMorale;
import java.util.List;

public interface TypePersonneMoraleService {
    public List<TypePersonneMorale> getAllTypePersonneMorale();
    public TypePersonneMorale createTypePersonneMorale(TypePersonneMorale typePersonneMorale);
    public TypePersonneMorale getTypePersonneMoraleById(Long id);
    public TypePersonneMorale updateTypePersonneMorale(TypePersonneMorale typePersonneMorale);
    public void deleteTypePersonneMoraleById(Long id);
}
