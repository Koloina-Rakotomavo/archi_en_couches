package com.example.banque.service;

import com.example.banque.model.*;
import com.example.banque.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
public class MobileDemoData {

    private final TypeProduitRepository typeRepository;
    private final ClientBancaireRepository clientRepository;
    private final ProduitBancaireRepository produitRepository;
    private final OperationRepository operationRepository;

    public MobileDemoData(TypeProduitRepository typeRepository,
                          ClientBancaireRepository clientRepository,
                          ProduitBancaireRepository produitRepository,
                          OperationRepository operationRepository) {
        this.typeRepository = typeRepository;
        this.clientRepository = clientRepository;
        this.produitRepository = produitRepository;
        this.operationRepository = operationRepository;
    }

    @Transactional
    public void initialiser() {
        if (produitRepository.findByNumeroCompteAndTypeProduit_IntituleIgnoreCase(
                "MOBILE001", "compte chèque").isPresent()) return;

        TypeProduit type = typeRepository.findByIntituleIgnoreCase("compte chèque")
                .stream().findFirst()
                .orElseGet(() -> typeRepository.save(new TypeProduit(0.5f, "compte chèque", 25f)));

        ClientBancaire client1 = new ClientBancaire();
        client1.addPersonne(new PersonnePhysique("Amiens", "Martin", "Alice"));
        clientRepository.save(client1);

        ClientBancaire client2 = new ClientBancaire();
        client2.addPersonne(new PersonnePhysique("Paris", "Durand", "Paul"));
        clientRepository.save(client2);

        ProduitBancaire compte1 = produitRepository.save(
                new ProduitBancaire(1000f, "MOBILE001", type, client1));
        produitRepository.save(new ProduitBancaire(500f, "MOBILE002", type, client2));

        for (int i = 0; i < 7; i++) {
            operationRepository.save(new Operation(Date.valueOf(LocalDate.now().minusDays(i)),
                    10f + i, "CREDIT", "Operation de demonstration " + (i + 1), compte1));
        }
    }
}
