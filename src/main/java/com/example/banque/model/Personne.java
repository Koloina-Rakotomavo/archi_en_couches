package com.example.banque.model;


import jakarta.persistence.*;

import java.util.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Personne {
    @Id
    @SequenceGenerator(name = "personne_sequence", sequenceName = "personne_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "personne_sequence")
    @Column(name = "id")
    protected Long id;

    @Column(name = "adresse", nullable = true, columnDefinition = "TEXT")
    protected String adresse;

    @ManyToMany (fetch = FetchType.EAGER,cascade = {CascadeType.ALL} , mappedBy = "personnes")
    protected List<ClientBancaire> clientsBancaires = new ArrayList<ClientBancaire>();

    public Personne() {}

    public Personne(String adresse) {
        this.adresse = adresse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public List<ClientBancaire> getClientsBancaires() {
        return clientsBancaires;
    }

    public void setClientsBancaires(List<ClientBancaire> clientsBancaires) {
        this.clientsBancaires = clientsBancaires;
    }

    public void addClientBancaire(ClientBancaire clientBancaire)
    {
        clientsBancaires.add(clientBancaire);

        // Je suis du côté non propriétaire de la relation
        // Si l'ajout d'une instance de la relation est initié par la personne, je dois rajouter
        // à la main la personne dans le client bancaire
        // Si l'ajout est initié par le client bancaire, la personne sera déjà dans le client bancaire
        // => test if
        if (!clientBancaire.getPersonnes().contains(this)) clientBancaire.addPersonne(this);
    }
    public void removeClientBancaireFromClientBancaire(ClientBancaire clientBancaire)
    {
        if (clientsBancaires.contains(clientBancaire)) {
            clientsBancaires.remove(clientBancaire);
        }

        // Je suis du côté non propriétaire de la relation
        // Si la suppression d'une instance de la relation est initiée par la personne, je dois enlever
        // à la main la personne dans le client bancaire
        // Si la suppression est initiée par le client bancaire, la personne sera déjà supprimée du client bancaire
        // => test if
        if (clientBancaire.getPersonnes().contains(this)) clientBancaire.removePersonne(this);
    }
    public void removeClientBancaire(ClientBancaire clientBancaire)
    {
        clientsBancaires.remove(clientBancaire);

        // Je suis du côté non propriétaire de la relation
        // Si la suppression d'une instance de la relation est initiée par la personne, je dois enlever
        // à la main la personne dans le client bancaire
        // Si la suppression est initiée par le client bancaire, la personne sera déjà supprimée du client bancaire
        // => test if
        if (clientBancaire.getPersonnes().contains(this)) clientBancaire.getPersonnes().remove(this);
    }
    @Override
    public String toString() {
        return "Personne{" + "Adresse='" + adresse + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Personne personne = (Personne) o;
        return Objects.equals(adresse, personne.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(adresse);
    }
}
