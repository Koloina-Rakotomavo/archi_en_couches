package com.example.banque.repository;

import com.example.banque.model.ProduitBancaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;

public interface ProduitBancaireRepository extends JpaRepository<ProduitBancaire, Long> {
    List<ProduitBancaire> findByTypeProduit_IntituleIgnoreCaseOrderByNumeroCompteAsc(String intitule);
    Optional<ProduitBancaire> findByNumeroCompteAndTypeProduit_IntituleIgnoreCase(
            String numeroCompte, String intitule);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from ProduitBancaire p where p.numeroCompte = :numeroCompte "
            + "and lower(p.typeProduit.intitule) = lower(:intitule)")
    Optional<ProduitBancaire> verrouillerCompte(@Param("numeroCompte") String numeroCompte,
                                               @Param("intitule") String intitule);
}
