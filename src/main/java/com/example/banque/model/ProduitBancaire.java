package com.example.banque.model;

import com.example.banque.model.TypeProduit;
import jakarta.persistence.*;
import java.util.Objects;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
public class ProduitBancaire {
    @Id
    @SequenceGenerator(name = "produit_bancaire_sequence", sequenceName = "produit_bancaire_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "produit_bancaire_sequence")

    @Column(name = "id")
    protected Long id;

    @Column(name = "solde_courant", nullable = false, columnDefinition = "FLOAT")
    private float solde_courant;

    @Column(name = "numero_compte", nullable = false, columnDefinition = "TEXT")
    private String numeroCompte;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_produit_id")
    private TypeProduit typeProduit;
    public ProduitBancaire(float solde_courant, String numeroCompte, TypeProduit TypeProduit) {
        this.solde_courant = solde_courant;
        this.numeroCompte = numeroCompte;
        this.typeProduit = TypeProduit;
        TypeProduit.getProduitsBancaires().add(this);
    }
    public ProduitBancaire() {
    }
    public TypeProduit getTypeProduit() {
        return typeProduit;
    }
    public void setTypeProduit(TypeProduit TypeProduit) {
        this.typeProduit = TypeProduit;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public float getSolde_courant() {
        return solde_courant;
    }
    public void setSolde_courant(float solde_courant) {
        this.solde_courant = solde_courant;
    }
    public String getNumeroCompte() {
        return numeroCompte;
    }
    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    @Override
    public String toString() {
        return "\nProduitBancaire{" +
                "\n\tid=" + id +
                ", \n\tsolde_courant=" + solde_courant +
                ", \n\tnumeroCompte =" + numeroCompte +
                ", \n\tTypeProduit=" + typeProduit +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProduitBancaire that = (ProduitBancaire) o;
        return this.id.equals(that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @PreRemove
    private void gererLiens()
    {
        // On enlève le produit bancaire de la liste des produits qui est dans le type de produit lié
        // Cela casse le lien de TypeProduit vers ce produit bancaire
        if (typeProduit!=null)
        {
            typeProduit.getProduitsBancaires().remove(this);
        }
        // On casse le lien du produit bancaire vers son type de produit
        typeProduit=null;
    }
}