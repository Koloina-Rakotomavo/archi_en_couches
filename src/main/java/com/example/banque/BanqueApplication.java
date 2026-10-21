package com.example.banque;


import com.example.banque.model.PersonneMorale;
import com.example.banque.model.PersonnePhysique;
import com.example.banque.model.ProduitBancaire;
import com.example.banque.model.TypeProduit;
import com.example.banque.repository.PersonneMoraleRepository;
import com.example.banque.repository.PersonnePhysiqueRepository;
import com.example.banque.repository.TypeProduitRepository;
import com.example.banque.repository.ProduitBancaireRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BanqueApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BanqueApplication.class, args);
    }

    @Bean
    CommandLineRunner testerBackend(TypeProduitRepository typeProduitRepository,
                                   PersonneMoraleRepository personneMoraleRepository,
                                   PersonnePhysiqueRepository personnePhysiqueRepository,
                                   ProduitBancaireRepository produitBancaireRepository)
    {
        return args -> {
            // On vide la table pour ne pas créer de doublons
            typeProduitRepository.deleteAll();
            // On crée une liste de types de produits que l'on insèrera d'un coup en base
            ArrayList<TypeProduit> listeTP = new ArrayList<TypeProduit>();
            // On crée quelques types de produits
            listeTP.add(new TypeProduit(
                    0,
                    "Mastercard",
                    20));
            listeTP.add(new TypeProduit(
                    0.3f,
                    "Livret épargne",
                    0));
            listeTP.add(new TypeProduit(
                    0.1f,
                    "Compte rémunéré",
                    0));
            listeTP.add(new TypeProduit(
                    0.5f,
                    "Prêt consommation",
                    0));
            listeTP.add(new TypeProduit(
                    0.3f,
                    "Prêt immobilier",
                    0));
            // On insère la liste dans la table
            typeProduitRepository.saveAll(listeTP);
            // Testons maintenant les requetes par defaut
            listeTP = new ArrayList<TypeProduit>();
            // Nombre de types de produits
            long nbTypeProduits = typeProduitRepository.count();
            System.out.println("\n***************** test count");
            System.out.println("\nil y a " + nbTypeProduits + " types de produits dans la base");
            // Rechercher un type de produit par id
            long id = 100;
            Optional<TypeProduit> typeProduit = typeProduitRepository.findById(id);
            System.out.println("\n***************** test findById");
            if (typeProduit.isPresent()) System.out.println("\nType de produit avec l'id : " + id + typeProduit);
            else System.out.println("Pas de type de produit avec la clé : " + id);
            // On récupère et on affiche les types de produits en base
            System.out.println("\n***************** Tous les types de produits non triees");
            System.out.println(typeProduitRepository.findAll());
            // On récupère les types de produits  en base triées par ordre alphabétique de l'intitulé
            System.out.println("\n***************** Tous les typesz de produits dans l'ordre alphabetique du typeProduit");
            System.out.println(typeProduitRepository.findAll(Sort.by(Sort.Direction.ASC, "intitule")));
            // Pagination
            int numeroPage = 3; // numéro de la page chargée. Attention, le numéro commence à 0. Ici, on récupère la quatrième page
            int taillePage = 1; // Nombre de types de produits par page.
            // L'instruction suivante récupère la 4ième page de données, chaque page contenant une seule donnée. Les données sont ici aussi triées par ordre alphabétique du type
            Page<TypeProduit> page = typeProduitRepository.findAll(PageRequest.of(3, 1, Sort.by(Sort.Direction.ASC, "intitule")));
            System.out.println("\n***************** Test pagination");
            System.out.println(page + "\n Contenu de la page" + page.getContent());
            ArrayList<TypeProduit> listetypeProduit =
                    typeProduitRepository.findByIntitule("Prêt consommation");

            System.out.println("\n*** Recherche par intitulé ***");
            System.out.println(listetypeProduit);
            System.out.println("*******************************\nRechercher les types de produits dont le type contient compte");
            listetypeProduit=typeProduitRepository.findByIntituleContains("compte");
            System.out.println(listetypeProduit);
            System.out.println("*******************************\nRechercher les types de produits dont le type contient compte");
            listetypeProduit=typeProduitRepository.findByIntituleLike("%compte%");
            System.out.println(listetypeProduit);
            System.out.println("*******************************\nRechercher les 3 derniers types de produits saisis en base");
            listetypeProduit=typeProduitRepository.findFirst3ByOrderByIdDesc();
            System.out.println(listetypeProduit);
            System.out.println("*******************************\nListe des produits dont la rentabilité est >=0.3 pour la banque");
            listetypeProduit=typeProduitRepository.findByTauxInteretAgiosGreaterThanEqualOrderByTauxInteretAgiosAsc(0.3f);
            System.out.println(listetypeProduit);
            PersonneMorale pm1 = new PersonneMorale("102bis rue du Vesuve", "SIRET1", "Pizza Tonio");
            PersonneMorale pm2 = new PersonneMorale("45 Boulevard du fleuve", "SIRET2", "Meubles cosy");
            PersonneMorale pm3 = new PersonneMorale("14 allee des platanes", "SIRET3", "Espaces tres verts");
            // Enregistrement en base
            personneMoraleRepository.save(pm1);
            personneMoraleRepository.save(pm2);
            personneMoraleRepository.save(pm3);
            // Affichage du résultat
            System.out.println(personneMoraleRepository.findAll());
            // Ajout de personnes physiques
            PersonnePhysique pp1 = new PersonnePhysique("19 rue des fleurs, 80000 Amiens", "Dupont", "Jean");
            personnePhysiqueRepository.save(pp1);
            PersonnePhysique pp2 = new PersonnePhysique("143 boulevard des landes, 64200 Anglet", "Eche", "Piou");
            personnePhysiqueRepository.save(pp2);
            PersonnePhysique pp3 = new PersonnePhysique("56 avenue de Paris, 60000 Beauvais", "Tristan", "Jacques");
            personnePhysiqueRepository.save(pp3);
            System.out.println(personnePhysiqueRepository.findAll());
            List<TypeProduit> typesProduits;
            List<ProduitBancaire> produitBancaires;
            TypeProduit tp1=new TypeProduit((float)0.2,"tp1",0);
            typeProduitRepository.save(tp1);
            TypeProduit tp2=new TypeProduit(3,"tp2",0);
            typeProduitRepository.save(tp2);
            TypeProduit tp3=new TypeProduit(0,"tp3",15);
            typeProduitRepository.save(tp3);

            tp3= typeProduitRepository.findById(tp3.getId()).orElseThrow();
            ProduitBancaire pb1 = new ProduitBancaire(1,"num1", tp3);
            produitBancaireRepository.save(pb1);
            tp2= typeProduitRepository.findById(tp2.getId()).orElseThrow();
            ProduitBancaire pb2 = new ProduitBancaire(2,"num2",tp2);
            produitBancaireRepository.save(pb2);
            tp3= typeProduitRepository.findById(tp3.getId()).orElseThrow();
            ProduitBancaire pb3 = new ProduitBancaire(3,"num3",tp3);
            produitBancaireRepository.save(pb3);

            typesProduits=typeProduitRepository.findAll();
            produitBancaires=produitBancaireRepository.findAll();
            System.out.println(produitBancaires);

        };
    }
}

