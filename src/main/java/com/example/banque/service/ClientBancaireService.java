package com.example.banque.service;

import com.example.banque.model.ClientBancaire;
import java.util.List;

public interface ClientBancaireService {
    public List<ClientBancaire> getAllClientBancaire();
    public ClientBancaire createClientBancaire(ClientBancaire clientBancaire);
    public ClientBancaire getClientBancaireById(Long id);
    public ClientBancaire updateClientBancaire(ClientBancaire clientBancaire);
    public void deleteClientBancaireById(Long id);
}
