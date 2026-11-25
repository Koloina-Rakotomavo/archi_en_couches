package com.example.banque.service;

import com.example.banque.dataTransferObjects.OperationDto;
import com.example.banque.dataTransferObjects.ProduitBancaireDto;
import com.example.banque.dataTransferObjects.TypeProduitDto;
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

    public MobileBanqueService(ProduitBancaireRepository produitRepository,
                               OperationRepository operationRepository,
                               TypeProduitRepository typeRepository) {
        this.produitRepository = produitRepository;
        this.operationRepository = operationRepository;
        this.typeRepository = typeRepository;
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

    private ProduitBancaire rechercherCompte(String numeroCompte) {
        return produitRepository.findByNumeroCompteAndTypeProduit_IntituleIgnoreCase(numeroCompte, COMPTE_CHEQUE)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte chèque introuvable"));
    }
}
