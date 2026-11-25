package com.example.banque.service;

import com.example.banque.dataTransferObjects.VirementDto;
import com.example.banque.model.*;
import com.example.banque.repository.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MobileBanqueServiceTest {
    private ProduitBancaireRepository produits;
    private OperationRepository operations;
    private TypeProduitRepository types;
    private MobileBanqueService service;
    private ProduitBancaire source;
    private ProduitBancaire destination;

    @BeforeEach
    void initialiser() {
        produits = mock(ProduitBancaireRepository.class);
        operations = mock(OperationRepository.class);
        types = mock(TypeProduitRepository.class);
        service = new MobileBanqueService(produits, operations, types, mock(EntityManager.class));

        TypeProduit type = new TypeProduit(0f, "compte chèque", 0f);
        source = new ProduitBancaire(1000f, "MOBILE001", type, new ClientBancaire());
        source.setId(1L);
        destination = new ProduitBancaire(500f, "MOBILE002", type, new ClientBancaire());
        destination.setId(2L);
        when(produits.verrouillerCompte("MOBILE001", "compte chèque")).thenReturn(Optional.of(source));
        when(produits.verrouillerCompte("MOBILE002", "compte chèque")).thenReturn(Optional.of(destination));
    }

    @Test
    void effectueUnVirementEtCreeDeuxOperations() {
        var resultat = service.effectuerVirement(new VirementDto("MOBILE001", "MOBILE002",
                new BigDecimal("100.25")));
        assertEquals(899.75f, source.getSolde_courant());
        assertEquals(600.25f, destination.getSolde_courant());
        assertEquals(0, new BigDecimal("899.75").compareTo(resultat.soldeSource()));
        ArgumentCaptor<Operation> captor = ArgumentCaptor.forClass(Operation.class);
        verify(operations, times(2)).save(captor.capture());
        assertEquals("DEBIT", captor.getAllValues().get(0).getType());
        assertEquals("CREDIT", captor.getAllValues().get(1).getType());
        assertSame(source, captor.getAllValues().get(0).getProduitBancaire());
        assertSame(destination, captor.getAllValues().get(1).getProduitBancaire());
    }

    @Test
    void refuseUnSoldeInsuffisant() {
        var erreur = assertThrows(ResponseStatusException.class, () ->
                service.effectuerVirement(new VirementDto("MOBILE001", "MOBILE002",
                        new BigDecimal("1001"))));
        assertEquals(HttpStatus.CONFLICT, erreur.getStatusCode());
        assertEquals(1000f, source.getSolde_courant());
        assertEquals(500f, destination.getSolde_courant());
        verifyNoInteractions(operations);
    }

    @Test
    void refuseLesMontantsInvalidesEtLesComptesIdentiques() {
        assertThrows(ResponseStatusException.class, () ->
                service.effectuerVirement(new VirementDto("MOBILE001", "MOBILE002", BigDecimal.ZERO)));
        assertThrows(ResponseStatusException.class, () ->
                service.effectuerVirement(new VirementDto("MOBILE001", "MOBILE001", BigDecimal.ONE)));
        assertThrows(ResponseStatusException.class, () ->
                service.effectuerVirement(new VirementDto("MOBILE001", "MOBILE002", new BigDecimal("1.001"))));
        verifyNoInteractions(produits, operations);
    }

    @Test
    void consulteLesOperationsLimiteesParLeRepository() {
        when(produits.findByNumeroCompteAndTypeProduit_IntituleIgnoreCase("MOBILE001", "compte chèque"))
                .thenReturn(Optional.of(source));
        Operation op = new Operation(Date.valueOf("2026-11-25"), 15f, "CREDIT", "Test", source);
        when(operations.findTop5ByProduitBancaire_IdOrderByDateOperationDescIdDesc(1L))
                .thenReturn(List.of(op));
        var resultat = service.getDernieresOperations("MOBILE001");
        assertEquals(1, resultat.size());
        assertEquals("Test", resultat.get(0).getIntituleOperation());
        verify(operations).findTop5ByProduitBancaire_IdOrderByDateOperationDescIdDesc(1L);
    }
}
