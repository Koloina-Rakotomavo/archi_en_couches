package com.example.banque.repository;

import com.example.banque.model.ClientBancaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientBancaireRepository extends JpaRepository<ClientBancaire, Long> {
}
