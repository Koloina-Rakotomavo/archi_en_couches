package com.example.banque.service.impl;

import com.example.banque.model.PersonneMorale;
import com.example.banque.repository.PersonneMoraleRepository;
import com.example.banque.service.PersonneMoraleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonneMoraleServiceImpl implements PersonneMoraleService {

    private PersonneMoraleRepository personneMoraleRepository;

    @Autowired
    public PersonneMoraleServiceImpl(PersonneMoraleRepository personneMoraleRepository) {
        this.personneMoraleRepository = personneMoraleRepository;
    }

    @Override
    public List<PersonneMorale> getAllPersonneMorale() {
        return personneMoraleRepository.findAll();
    }

    @Override
    public PersonneMorale createPersonneMorale(PersonneMorale personneMorale) {
        return personneMoraleRepository.save(personneMorale);
    }

    @Override
    public PersonneMorale getPersonneMoraleById(Long id) {
        return personneMoraleRepository.findById(id).orElseThrow();
    }

    @Override
    public PersonneMorale updatePersonneMorale(PersonneMorale personneMorale) {
        return personneMoraleRepository.save(personneMorale);
    }

    @Override
    public void deletePersonneMoraleById(Long id) {
        personneMoraleRepository.findById(id).orElseThrow();
        personneMoraleRepository.deleteById(id);
    }
}
