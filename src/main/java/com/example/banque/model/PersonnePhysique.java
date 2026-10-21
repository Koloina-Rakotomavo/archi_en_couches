package com.example.banque.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "PersonnePhysique")
@Table(name = "personne_physique")

public class PersonnePhysique extends Personne {
    @Column(name = "nom", nullable = false, columnDefinition = "TEXT")
    private String nom;
    @Column(name = "prenom", nullable = true, columnDefinition = "TEXT")
    private String prenom;

    public PersonnePhysique() {
        super();
    }

    public PersonnePhysique(String adresse, String nom, String prenom) {
        super(adresse);
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "PersonnePhysique{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}