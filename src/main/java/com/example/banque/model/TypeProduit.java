package com.example.banque.model;

import jakarta.persistence.*;

import java.util.Objects;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "TypeProduit")
@Table(name = "type_produit")

public class TypeProduit {
    @Id
    @SequenceGenerator(name = "typeproduit_sequence", sequenceName = "typeproduit_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "typeproduit_sequence")

    @Column(name = "id")
    private Long id;

    @Column(name = "tx_interet_agios", nullable = true, columnDefinition = "FLOAT")
    private float tauxInteretAgios;

    @Column(name = "intitule", nullable = false, columnDefinition = "TEXT")
    private String intitule;

    @Column(name = "cotisation_carte", nullable = true, columnDefinition = "FLOAT")
    private float cotisationCarte;

    public TypeProduit() {
    }

    public TypeProduit(float tauxInteretAgios, String intitule, float cotisationCarte) {
        this.tauxInteretAgios = tauxInteretAgios;
        this.intitule = intitule;
        this.cotisationCarte = cotisationCarte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getTauxInteretAgios() {
        return tauxInteretAgios;
    }

    public void setTauxInteretAgios(float tauxInteretAgios) {
        this.tauxInteretAgios = tauxInteretAgios;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public float getCotisationCarte() {
        return cotisationCarte;
    }

    public void setCotisationCarte(float cotisationCarte) {
        this.cotisationCarte = cotisationCarte;
    }

    @Override
    public String toString() {
        return "TypeProduit{" +
                "id=" + id +
                ", tauxInteretAgios=" + tauxInteretAgios +
                ", intitule='" + intitule + '\'' +
                ", cotisationCarte=" + cotisationCarte +
                '}'+'\n';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TypeProduit that = (TypeProduit) o;
        return Float.compare(tauxInteretAgios, that.tauxInteretAgios) == 0 && Float.compare(cotisationCarte, that.cotisationCarte) == 0 && Objects.equals(intitule, that.intitule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tauxInteretAgios, intitule, cotisationCarte);
    }
}