package com.example.banque.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "operation_bancaire")
public class Operation {
    @Id
    @SequenceGenerator(name = "operation_sequence", sequenceName = "operation_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_sequence")
    private Long id;

    @Column(name = "date_operation", nullable = false, columnDefinition = "DATE")
    private Date dateOperation;

    @Column(name = "montant", nullable = false, columnDefinition = "FLOAT")
    private float montant;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "libelle", columnDefinition = "TEXT")
    private String libelle;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "produit_bancaire_id", nullable = false)
    private ProduitBancaire produitBancaire;

    public Operation() {}

    public Operation(Date dateOperation, float montant, String type, String libelle,
                     ProduitBancaire produitBancaire) {
        this.dateOperation = dateOperation;
        this.montant = montant;
        this.type = type;
        this.libelle = libelle;
        setProduitBancaire(produitBancaire);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getDateOperation() { return dateOperation; }
    public void setDateOperation(Date dateOperation) { this.dateOperation = dateOperation; }
    public float getMontant() { return montant; }
    public void setMontant(float montant) { this.montant = montant; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public ProduitBancaire getProduitBancaire() { return produitBancaire; }

    public void setProduitBancaire(ProduitBancaire nouveauProduit) {
        if (produitBancaire == nouveauProduit) return;
        if (produitBancaire != null) {
            produitBancaire.getOperations().removeIf(operation -> operation == this);
        }
        produitBancaire = nouveauProduit;
        if (nouveauProduit != null &&
                nouveauProduit.getOperations().stream().noneMatch(operation -> operation == this)) {
            nouveauProduit.getOperations().add(this);
        }
    }

    @Override
    public String toString() {
        return "Operation{id=" + id + ", dateOperation=" + dateOperation
                + ", montant=" + montant + ", type='" + type + "', libelle='" + libelle + "'}";
    }
}
