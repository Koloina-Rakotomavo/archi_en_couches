package com.example.banque.repository;

import com.example.banque.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {
    List<Operation> findTop5ByProduitBancaire_IdOrderByDateOperationDescIdDesc(Long produitId);
}
