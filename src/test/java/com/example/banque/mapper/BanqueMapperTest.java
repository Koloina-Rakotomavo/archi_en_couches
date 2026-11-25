package com.example.banque.mapper;

import com.example.banque.model.*;
import org.junit.jupiter.api.Test;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class BanqueMapperTest {

    @Test
    void convertitUnTypeProduitDansLesDeuxSens() {
        TypeProduit type = new TypeProduit(0.5f, "compte chèque", 25f);
        var dto = BanqueMapper.toDto(type);
        assertEquals("compte chèque", dto.getIntitule());
        assertEquals(type, BanqueMapper.toEntity(dto));
    }

    @Test
    void aplatitLesPersonnesSansRelationsInverses() {
        ClientBancaire client = new ClientBancaire();
        client.addPersonne(new PersonnePhysique("Amiens", "Dupont", "Jean"));
        client.addPersonne(new PersonneMorale("Paris", "123", "Entreprise test"));
        var dto = BanqueMapper.toDto(client);
        assertEquals(2, dto.getPersonneDtos().size());
        assertTrue(dto.getPersonneDtos().stream().anyMatch(p ->
                "Dupont".equals(p.getNom()) && "PHYSIQUE".equals(p.getTypePersonne())));
        assertTrue(dto.getPersonneDtos().stream().anyMatch(p ->
                "Entreprise test".equals(p.getNom()) && "MORALE".equals(p.getTypePersonne())));
    }

    @Test
    void convertitLeProduitEtSesOperations() {
        ProduitBancaire produit = new ProduitBancaire(100f, "TEST",
                new TypeProduit(0f, "compte chèque", 0f), new ClientBancaire());
        produit.setId(9L);
        new Operation(Date.valueOf("2026-11-25"), 10f, "CREDIT", "Test", produit);
        var dto = BanqueMapper.toDto(produit);
        assertEquals(1, dto.getOperationDtos().size());
        assertEquals(Long.valueOf(9), dto.getOperationDtos().iterator().next().getProduitBancaireId());
        assertTrue(BanqueMapper.toDtoSansOperations(produit).getOperationDtos().isEmpty());
    }
}
