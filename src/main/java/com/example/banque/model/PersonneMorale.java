package com.example.banque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "PersonneMorale")
@Table(name = "personne_morale")
public class PersonneMorale extends Personne {

    @Column(name = "siret", nullable = true, columnDefinition = "TEXT")
    private String SIRET;
    @Column(name = "raison_sociale", nullable = false, columnDefinition = "TEXT")
    private String raisonSociale;

    public PersonneMorale() {}

    public PersonneMorale(String adresse, String SIRET, String raisonSociale) {
        super(adresse);
        this.SIRET = SIRET;
        this.raisonSociale = raisonSociale;
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
