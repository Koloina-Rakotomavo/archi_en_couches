package com.example.banque.dataTransferObjects;

import java.util.Objects;

public class TypeProduitDto {
    private float cotisationCarte;
    private float tauxInteretAgios;
    private String intitule;

    public TypeProduitDto() {}

    public TypeProduitDto(float tauxInteretAgios, String intitule, float cotisationCarte) {
        this.tauxInteretAgios = tauxInteretAgios;
        this.intitule = intitule;
        this.cotisationCarte = cotisationCarte;
    }

    public float getCotisationCarte() { return cotisationCarte; }
    public void setCotisationCarte(float cotisationCarte) { this.cotisationCarte = cotisationCarte; }
    public float getTauxInteretAgios() { return tauxInteretAgios; }
    public void setTauxInteretAgios(float tauxInteretAgios) { this.tauxInteretAgios = tauxInteretAgios; }
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TypeProduitDto dto)) return false;
        return Float.compare(cotisationCarte, dto.cotisationCarte) == 0
                && Float.compare(tauxInteretAgios, dto.tauxInteretAgios) == 0
                && Objects.equals(intitule, dto.intitule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cotisationCarte, tauxInteretAgios, intitule);
    }

    @Override
    public String toString() {
        return "TypeProduitDto{intitule='" + intitule
                + "', tauxInteretAgios=" + tauxInteretAgios
                + ", cotisationCarte=" + cotisationCarte + "}";
    }
}
