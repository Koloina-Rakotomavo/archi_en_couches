package com.example.banque.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type_personne_morale")
public class TypePersonneMorale {
    @Id
    @SequenceGenerator(name = "type_personne_morale_sequence",
            sequenceName = "type_personne_morale_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "type_personne_morale_sequence")
    private Long id;

    @Column(name = "intitule", nullable = false, columnDefinition = "TEXT")
    private String intitule;

    // Côté non propriétaire : référence l'attribut de PersonneMorale.
    @OneToMany(mappedBy = "typePersonneMorale", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonneMorale> personnesMorales = new ArrayList<>();

    public TypePersonneMorale() {}

    public TypePersonneMorale(String intitule) {
        this.intitule = intitule;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    public List<PersonneMorale> getPersonnesMorales() { return personnesMorales; }

    public void addPersonneMorale(PersonneMorale personneMorale) {
        personneMorale.setTypePersonneMorale(this);
    }

    public void removePersonneMorale(PersonneMorale personneMorale) {
        if (personneMorale.getTypePersonneMorale() == this) {
            personneMorale.setTypePersonneMorale(null);
        }
    }

    @Override
    public String toString() {
        return "TypePersonneMorale{id=" + id + ", intitule='" + intitule + "'}";
    }
}
