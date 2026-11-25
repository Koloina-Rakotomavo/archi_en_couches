package com.example.banque.dataTransferObjects;

import java.sql.Date;

public class OperationDto {
    private String intituleOperation;
    private float montant;
    private Date dateOperation;
    private String typeOperation;
    private Long produitBancaireId;

    public OperationDto() {}

    public OperationDto(String intituleOperation, float montant, String typeOperation, Date dateOperation) {
        this.intituleOperation = intituleOperation;
        this.montant = montant;
        this.typeOperation = typeOperation;
        this.dateOperation = dateOperation;
    }

    public String getIntituleOperation() { return intituleOperation; }
    public void setIntituleOperation(String intituleOperation) { this.intituleOperation = intituleOperation; }
    public float getMontant() { return montant; }
    public void setMontant(float montant) { this.montant = montant; }
    public Date getDateOperation() { return dateOperation; }
    public void setDateOperation(Date dateOperation) { this.dateOperation = dateOperation; }
    public String getTypeOperation() { return typeOperation; }
    public void setTypeOperation(String typeOperation) { this.typeOperation = typeOperation; }
    public Long getProduitBancaireId() { return produitBancaireId; }
    public void setProduitBancaireId(Long produitBancaireId) { this.produitBancaireId = produitBancaireId; }
}
