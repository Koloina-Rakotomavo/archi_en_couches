package com.example.banque.service;

import com.example.banque.dataTransferObjects.OperationDto;
import com.example.banque.dataTransferObjects.ProduitBancaireDto;
import com.example.banque.dataTransferObjects.TypeProduitDto;
import com.example.banque.dataTransferObjects.VirementDto;
import com.example.banque.dataTransferObjects.VirementResultatDto;
import com.example.banque.model.Operation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import com.example.banque.mapper.BanqueMapper;
import com.example.banque.model.ProduitBancaire;
import com.example.banque.repository.OperationRepository;
import com.example.banque.repository.ProduitBancaireRepository;
import com.example.banque.repository.TypeProduitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MobileBanqueService {

    private static final String COMPTE_CHEQUE = "compte chèque";

    private final ProduitBancaireRepository produitRepository;
    private final OperationRepository operationRepository;
    private final TypeProduitRepository typeRepository;
    private final EntityManager entityManager;

    public MobileBanqueService(ProduitBancaireRepository produitRepository,
                               OperationRepository operationRepository,
                               TypeProduitRepository typeRepository, EntityManager entityManager) {
        this.produitRepository = produitRepository;
        this.operationRepository = operationRepository;
        this.typeRepository = typeRepository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public List<TypeProduitDto> getTypesProduits() {
        return typeRepository.findByIntituleIgnoreCase(COMPTE_CHEQUE)
                .stream().map(BanqueMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<ProduitBancaireDto> getProduits() {
        return produitRepository.findByTypeProduit_IntituleIgnoreCaseOrderByNumeroCompteAsc(COMPTE_CHEQUE)
                .stream().map(BanqueMapper::toDtoSansOperations).toList();
    }

    @Transactional(readOnly = true)
    public ProduitBancaireDto getCompte(String numeroCompte) {
        return BanqueMapper.toDtoSansOperations(rechercherCompte(numeroCompte));
    }

    @Transactional(readOnly = true)
    public List<OperationDto> getDernieresOperations(String numeroCompte) {
        ProduitBancaire compte = rechercherCompte(numeroCompte);
        return operationRepository.findTop5ByProduitBancaire_IdOrderByDateOperationDescIdDesc(compte.getId())
                .stream().map(BanqueMapper::toDto).toList();
    }

    @Transactional
    public VirementResultatDto effectuerVirement(VirementDto dto) {
        if (dto == null || dto.getCompteSource() == null || dto.getCompteDestination() == null
                || dto.getCompteSource().isBlank() || dto.getCompteDestination().isBlank()
                || dto.getMontant() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comptes et montant obligatoires");
        }
        String source = dto.getCompteSource().trim();
        String destination = dto.getCompteDestination().trim();
        BigDecimal montant = dto.getMontant().stripTrailingZeros();
        if (source.equalsIgnoreCase(destination) || montant.signum() <= 0 || montant.scale() > 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Comptes distincts et montant positif avec deux décimales maximum requis");
        }
        float montantOperation = versFloat(montant);

        // Toujours verrouiller les comptes dans le même ordre.
        boolean sourceEnPremier = String.CASE_INSENSITIVE_ORDER.compare(source, destination) < 0;
        ProduitBancaire premier = verrouillerCompte(sourceEnPremier ? source : destination);
        ProduitBancaire second = verrouillerCompte(sourceEnPremier ? destination : source);
        ProduitBancaire compteSource = sourceEnPremier ? premier : second;
        ProduitBancaire compteDestination = sourceEnPremier ? second : premier;
        if (compteSource.getId().equals(compteDestination.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Comptes identiques");
        }
        // Les associations JPA peuvent avoir chargé un compte avant son verrouillage.
        entityManager.refresh(compteSource, LockModeType.PESSIMISTIC_WRITE);
        entityManager.refresh(compteDestination, LockModeType.PESSIMISTIC_WRITE);

        BigDecimal soldeSource = soldeDecimal(compteSource);
        BigDecimal soldeDestination = soldeDecimal(compteDestination);
        if (soldeSource.compareTo(montant) < 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solde insuffisant");
        }

        BigDecimal nouveauSoldeSource = soldeSource.subtract(montant);
        BigDecimal nouveauSoldeDestination = soldeDestination.add(montant);
        float sourceFloat = versFloat(nouveauSoldeSource);
        float destinationFloat = versFloat(nouveauSoldeDestination);

        compteSource.setSolde_courant(sourceFloat);
        compteDestination.setSolde_courant(destinationFloat);
        Date date = Date.valueOf(LocalDate.now());

        operationRepository.save(new Operation(date, montantOperation, "DEBIT",
                "Virement vers " + compteDestination.getNumeroCompte(), compteSource));
        operationRepository.save(new Operation(date, montantOperation, "CREDIT",
                "Virement de " + compteSource.getNumeroCompte(), compteDestination));

        return new VirementResultatDto(compteSource.getNumeroCompte(),
                compteDestination.getNumeroCompte(), montant, nouveauSoldeSource, nouveauSoldeDestination);
    }

    private ProduitBancaire verrouillerCompte(String numeroCompte) {
        return produitRepository.verrouillerCompte(numeroCompte, COMPTE_CHEQUE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte chèque introuvable"));
    }

    private BigDecimal soldeDecimal(ProduitBancaire compte) {
        float solde = compte.getSolde_courant();
        if (!Float.isFinite(solde)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Solde invalide");
        }
        return new BigDecimal(Float.toString(solde));
    }

    // Le modèle du TD utilise float ; refuser une conversion qui perd des décimales.
    private float versFloat(BigDecimal valeur) {
        float resultat = valeur.floatValue();
        if (!Float.isFinite(resultat)
                || new BigDecimal(Float.toString(resultat)).compareTo(valeur) != 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Montant non représentable dans le modèle actuel");
        }
        return resultat;
    }

    private ProduitBancaire rechercherCompte(String numeroCompte) {
        return produitRepository.findByNumeroCompteAndTypeProduit_IntituleIgnoreCase(numeroCompte, COMPTE_CHEQUE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte chèque introuvable"));
    }
}
