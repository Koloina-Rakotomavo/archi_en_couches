package com.example.banque.model;

import jakarta.persistence.*;
import java.util.Objects;
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
