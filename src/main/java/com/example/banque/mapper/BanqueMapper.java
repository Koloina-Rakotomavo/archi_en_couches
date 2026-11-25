package com.example.banque.mapper;

import com.example.banque.dataTransferObjects.*;
import com.example.banque.model.*;

import java.util.LinkedHashSet;
import java.util.Set;

public final class BanqueMapper {

    private BanqueMapper() {}

    public static TypeProduitDto toDto(TypeProduit type) {
        if (type == null) return null;
        return new TypeProduitDto(type.getTauxInteretAgios(), type.getIntitule(),
                type.getCotisationCarte());
    }

    public static TypeProduit toEntity(TypeProduitDto dto) {
        if (dto == null) return null;
        return new TypeProduit(dto.getTauxInteretAgios(), dto.getIntitule(),
                dto.getCotisationCarte());
    }

    public static PersonneDto toDto(Personne personne) {
        if (personne == null) return null;
        if (personne instanceof PersonnePhysique physique) {
            return new PersonneDto(physique.getNom(), physique.getAdresse(), "PHYSIQUE");
        }
        if (personne instanceof PersonneMorale morale) {
            return new PersonneDto(morale.getRaisonSociale(), morale.getAdresse(), "MORALE");
        }
        throw new IllegalArgumentException("Type de personne inconnu");
    }

    // Le DTO ne contient ni identifiant, ni prénom, ni SIRET.
    // On applique les champs visibles à une personne existante.
    public static void updateEntity(PersonneDto dto, Personne personne) {
        if (personne instanceof PersonnePhysique physique && "PHYSIQUE".equals(dto.getTypePersonne())) {
            physique.setNom(dto.getNom());
        } else if (personne instanceof PersonneMorale morale && "MORALE".equals(dto.getTypePersonne())) {
            morale.setRaisonSociale(dto.getNom());
        } else {
            throw new IllegalArgumentException("Type de personne incompatible");
        }
        personne.setAdresse(dto.getAdresse());
    }

    public static ClientBancaireDto toDto(ClientBancaire client) {
        if (client == null) return null;
        Set<PersonneDto> personnes = new LinkedHashSet<>();
        for (Personne personne : client.getPersonnes()) {
            personnes.add(toDto(personne));
        }
        return new ClientBancaireDto(personnes);
    }

    // Le service doit résoudre les titulaires existants avant la conversion inverse.
    public static ClientBancaire toEntity(ClientBancaireDto dto, Iterable<Personne> titulaires) {
        if (dto == null) return null;
        ClientBancaire client = new ClientBancaire();
        for (Personne personne : titulaires) client.addPersonne(personne);
        return client;
    }

    public static OperationDto toDto(Operation operation) {
        if (operation == null) return null;
        OperationDto dto = new OperationDto(operation.getLibelle(), operation.getMontant(),
                operation.getType(), operation.getDateOperation());
        if (operation.getProduitBancaire() != null) {
            dto.setProduitBancaireId(operation.getProduitBancaire().getId());
        }
        return dto;
    }

    public static Operation toEntity(OperationDto dto, ProduitBancaire produit) {
        if (dto == null) return null;
        if (produit == null || (dto.getProduitBancaireId() != null
                && !dto.getProduitBancaireId().equals(produit.getId()))) {
            throw new IllegalArgumentException("Produit bancaire incompatible");
        }
        return new Operation(dto.getDateOperation(), dto.getMontant(), dto.getTypeOperation(),
                dto.getIntituleOperation(), produit);
    }

    public static ProduitBancaireDto toDtoSansOperations(ProduitBancaire produit) {
        if (produit == null) return null;
        return new ProduitBancaireDto(produit.getNumeroCompte(), produit.getSolde_courant(),
                toDto(produit.getTypeProduit()), toDto(produit.getClientBancaire()));
    }

    public static ProduitBancaireDto toDto(ProduitBancaire produit) {
        if (produit == null) return null;
        ProduitBancaireDto dto = toDtoSansOperations(produit);
        for (Operation operation : produit.getOperations()) {
            dto.getOperationDtos().add(toDto(operation));
        }
        return dto;
    }

    // Les relations sont résolues par le service, jamais par le mapper.
    public static ProduitBancaire toEntity(ProduitBancaireDto dto, TypeProduit type,
                                           ClientBancaire client) {
        if (dto == null) return null;
        if (type == null || client == null) {
            throw new IllegalArgumentException("Type et client obligatoires");
        }
        return new ProduitBancaire(dto.getSoldeCourant(), dto.getNumeroCompte(), type, client);
    }
}
