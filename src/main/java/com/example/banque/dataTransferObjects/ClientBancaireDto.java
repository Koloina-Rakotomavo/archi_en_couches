package com.example.banque.dataTransferObjects;

import java.util.LinkedHashSet;
import java.util.Set;

public class ClientBancaireDto {
    private Set<PersonneDto> personneDtos = new LinkedHashSet<>();

    public ClientBancaireDto() {}

    public ClientBancaireDto(Set<PersonneDto> personneDtos) {
        setPersonneDtos(personneDtos);
    }

    public Set<PersonneDto> getPersonneDtos() { return personneDtos; }
    public void setPersonneDtos(Set<PersonneDto> personneDtos) {
        this.personneDtos = personneDtos == null ? new LinkedHashSet<>() : new LinkedHashSet<>(personneDtos);
    }

    public void addPersonne(PersonneDto personneDto) { personneDtos.add(personneDto); }
    public void removePersonne(PersonneDto personneDto) { personneDtos.remove(personneDto); }
}
