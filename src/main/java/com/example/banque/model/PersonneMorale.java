package com.example.banque.model;

import jakarta.persistence.*;

@Entity(name = "PersonneMorale")
@Table(name = "personne_morale")
public class PersonneMorale extends Personne {

    @Column(name = "siret", nullable = true, columnDefinition = "TEXT")
    private String SIRET;
    @Column(name = "raison_sociale", nullable = false, columnDefinition = "TEXT")
    private String raisonSociale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_personne_morale_id")
    private TypePersonneMorale typePersonneMorale;

    public PersonneMorale() {}

    public PersonneMorale(String adresse, String SIRET, String raisonSociale) {
        super(adresse);
        this.SIRET = SIRET;
        this.raisonSociale = raisonSociale;
    }

    public PersonneMorale(String adresse, String SIRET, String raisonSociale,
                          TypePersonneMorale typePersonneMorale) {
        this(adresse, SIRET, raisonSociale);
        setTypePersonneMorale(typePersonneMorale);
    }

    public TypePersonneMorale getTypePersonneMorale() {
        return typePersonneMorale;
    }

    public void setTypePersonneMorale(TypePersonneMorale nouveauType) {
        if (this.typePersonneMorale == nouveauType) return;
        if (this.typePersonneMorale != null) {
            this.typePersonneMorale.getPersonnesMorales().removeIf(pm -> pm == this);
        }
        this.typePersonneMorale = nouveauType;
        if (nouveauType != null &&
                nouveauType.getPersonnesMorales().stream().noneMatch(pm -> pm == this)) {
            nouveauType.getPersonnesMorales().add(this);
        }
    }

    @PreRemove
    private void gererLiens() {
        setTypePersonneMorale(null);
    }

    public String getSIRET() {
        return SIRET;
    }

    public void setSIRET(String SIRET) {
        this.SIRET = SIRET;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    @Override
    public String toString() {
        return "\nPersonneMorale{" +
                "\nid=" + id +
                ", \nSIRET='" + SIRET + '\'' +
                ", \nraisonSociale='" + raisonSociale + '\'' +
                ", \nadresse='" + adresse + '\'' +
                "}\n";
    }

    public String nomComplet() {
        return raisonSociale;
    }
}
