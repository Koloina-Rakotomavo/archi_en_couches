package com.example.banque.service.impl;

import com.example.banque.model.ClientBancaire;
import com.example.banque.repository.ClientBancaireRepository;
import com.example.banque.service.ClientBancaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientBancaireServiceImpl implements ClientBancaireService {

    private ClientBancaireRepository clientBancaireRepository;

    @Autowired
    public ClientBancaireServiceImpl(ClientBancaireRepository clientBancaireRepository) {
        this.clientBancaireRepository = clientBancaireRepository;
    }

    @Override
    public List<ClientBancaire> getAllClientBancaire() {
        return clientBancaireRepository.findAll();
    }

    @Override
    public ClientBancaire createClientBancaire(ClientBancaire clientBancaire) {
        return clientBancaireRepository.save(clientBancaire);
    }

    @Override
    public ClientBancaire getClientBancaireById(Long id) {
        return clientBancaireRepository.findById(id).orElseThrow();
    }

    @Override
    public ClientBancaire updateClientBancaire(ClientBancaire clientBancaire) {
        return clientBancaireRepository.save(clientBancaire);
    }

    @Override
    public void deleteClientBancaireById(Long id) {
        clientBancaireRepository.findById(id).orElseThrow();
        clientBancaireRepository.deleteById(id);
    }
}
