package com.example.banque.dataTransferObjects;

import java.math.BigDecimal;

public class VirementDto {
    private String compteSource;
    private String compteDestination;
    private BigDecimal montant;

    public VirementDto() {}

    public VirementDto(String compteSource, String compteDestination, BigDecimal montant) {
        this.compteSource = compteSource;
        this.compteDestination = compteDestination;
        this.montant = montant;
    }

    public String getCompteSource() { return compteSource; }
    public void setCompteSource(String compteSource) { this.compteSource = compteSource; }
    public String getCompteDestination() { return compteDestination; }
    public void setCompteDestination(String compteDestination) { this.compteDestination = compteDestination; }
    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }
}
