package com.example.banque.model;

import jakarta.persistence.*;

import java.util.*;

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

    // Création du lien OneToManby côté NON propriétaire
    // mappedBy contient le nom de l'attribut ManyToOne dans ProduitBancaire
    @OneToMany(mappedBy = "typeProduit", fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<ProduitBancaire> produitsBancaires=new ArrayList<>();

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
    public List<ProduitBancaire> getProduitsBancaires() {return produitsBancaires;}

    public void setProduitsBancaires(List<ProduitBancaire> produitsBancaires) {

        this.produitsBancaires = produitsBancaires;

    }
    public void addProduitBancaire(ProduitBancaire produitBancaire)
    {
        this.produitsBancaires.add(produitBancaire);
    }
    public void removeProduitBancaire(ProduitBancaire produitBancaire)
    {
        this.produitsBancaires.remove(produitBancaire);
        produitBancaire.setTypeProduit(null);
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