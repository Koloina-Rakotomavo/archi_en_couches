package com.example.banque.service;

import com.example.banque.model.PersonnePhysique;
import java.util.List;

public interface PersonnePhysiqueService {
    public List<PersonnePhysique> getAllPersonnePhysique();
    public PersonnePhysique createPersonnePhysique(PersonnePhysique personnePhysique);
    public PersonnePhysique getPersonnePhysiqueById(Long id);
    public PersonnePhysique updatePersonnePhysique(PersonnePhysique personnePhysique);
    public void deletePersonnePhysiqueById(Long id);
}
